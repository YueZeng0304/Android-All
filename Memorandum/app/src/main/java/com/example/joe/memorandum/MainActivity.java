 package com.example.joe.memorandum;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.joe.memorandum.sqlite.*;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity implements SearchView.OnQueryTextListener{

    SimpleAdapter listAdapter;
    int index = 0;// 长按指定数据的索引
    PopupWindow mPopupWindow = null;
    ArrayList<HashMap<String,String>> showlist,list = Utils.getList();
    DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "zhaochen_memorandum_db");//新建数据库
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchview = (SearchView)findViewById(R.id.searchView);
        searchview.setOnQueryTextListener(this);
        //初始化载入数据库的数据
        list = Utils.getList();
        if(list.isEmpty())
            loadFromDatabase(list);      //先检查缓存，若没有数据再从数据库加载

//        Utils.MillisToDate(list);
        listAdapter = new SimpleAdapter(this,list,R.layout.list_item,new String[]{"datetime","content"},
                new int[]{R.id.datetime,R.id.content});
        setListAdapter(listAdapter);                      //将备忘录数据显示出来
        Button button = (Button)findViewById(R.id.createButton);//新建
        button.setOnClickListener(new ClickListener());//新建
        getListView().setOnItemClickListener(new ListItemClickListener());//点击文本事件
        getListView().setOnItemLongClickListener( new ItemLongClickListener());//长按文本事件

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//方向
            Intent intent = new Intent(MainActivity.this, ActivityLand.class);
            startActivity(intent);
        }
    }

    class ClickListener implements OnClickListener{//新建

        @Override
        public void onClick(View v) {
            Utils.DateToMillis(list);
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            Bundle b = new Bundle();
            b.putString("datetime", "");
            b.putString("content", "");
            b.putString("alerttime","");
            intent.putExtra("android.intent.extra.INTENT", b);
            startActivity(intent);                                //启动转到的编辑Activity
        }
    }
    class ItemLongClickListener implements OnItemLongClickListener{//长按文本事件

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            index = position;

            View popupView = getLayoutInflater().inflate(R.layout.popupwindow,null);//分享或删除
            mPopupWindow = new PopupWindow(popupView,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setAnimationStyle(R.style.popupAnimation);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//这里必须设置这句，使得touch弹窗以外的地方或者按返回键才会消失而且Drawable不能用null代替
            mPopupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);

            Button deleteButton = (Button)popupView.findViewById(R.id.deleteButton);
            Button shareButton = (Button)popupView.findViewById(R.id.shareButton);

            deleteButton.setOnClickListener(new OnClickListener(){//删除
                @Override
                public void onClick(View v) {
                    deleteItem(index);
                    mPopupWindow.dismiss();
                }
            });
            shareButton.setOnClickListener(new OnClickListener(){//分享
                @Override
                public void onClick(View v) {
                    shareItem(index);
                    mPopupWindow.dismiss();
                }
            });
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//创建选项菜单
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return false;
    }

    private void loadFromDatabase(ArrayList<HashMap<String,String>> list){//从数据库加载
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("user", new String[] { "datetime", "content","alerttime" }, null,
                null, null, null,"datetime desc");
        while (cursor.moveToNext()) {//光标读取
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String datetime = cursor.getString(0);//日期
                String content = cursor.getString(1);//内容
                String alerttime = cursor.getString(2);//闹钟时间
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("datetime", datetime);
                map.put("content", content);
                map.put("alerttime", alerttime);
                list.add(map);//加载
            }
        }
    }
    class ListItemClickListener implements OnItemClickListener{//点击文本事件

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent itemintent = new Intent(MainActivity.this,EditActivity.class);
            Utils.DateToMillis(list);
            Bundle b = new Bundle();
            b.putString("datetime", Utils.getItem(position).get("datetime"));
            b.putString("content", Utils.getItem(position).get("content"));//显示内容
            b.putString("alerttime", Utils.getItem(position).get("alerttime"));
            b.putInt("index", position);
            itemintent.putExtra("android.intent.extra.INTENT", b);
            startActivity(itemintent);                                //启动转到的编辑Activity
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//选择选项
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return true;
    }


    @Override
    protected void onRestart() {//重启
        super.onRestart();
        Utils.sort();//排序
        Utils.MillisToDate(list);//转换为日期
        getListView().setOnItemClickListener(new ListItemClickListener());
        listAdapter = new SimpleAdapter(this,list,R.layout.list_item,new String[]{"datetime","content"},
                new int[]{R.id.datetime,R.id.content});//内容适配器
        setListAdapter(listAdapter);
//        listAdapter.notifyDataSetChanged();                           //更新ListView的数据显示
//        Utils.DateToMillis();
    }
    @Override
    public void onBackPressed() {//返回
        Utils.DateToMillis(list);
        this.finish();
        super.onBackPressed();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {//查询文本提交
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {//查询文本改变
        list = Utils.getList();
        if(newText != null){
            showlist = new ArrayList<HashMap<String,String>>();
            for(int i=0;i<list.size();i++){
                String content = list.get(i).get("content");
                if(content.contains(newText)){//如果包含新文本
                    HashMap<String,String> map = list.get(i);
                    map.put("id", String.valueOf(i));
                    showlist.add(map);//map加入list
                }
            }
//			listAdapter.notifyDataSetChanged();
            listAdapter = new SimpleAdapter(this,showlist,R.layout.list_item,new String[]{"datetime","content"},
                    new int[]{R.id.datetime,R.id.content});//适配器
            setListAdapter(listAdapter);
            getListView().setOnItemClickListener(new OnItemClickListener() {//点击文本事件
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent searchintent = new Intent(MainActivity.this,EditActivity.class);
                    Utils.DateToMillis(list);
                    Bundle b = new Bundle();
                    b.putString("datetime", showlist.get(position).get("datetime"));
                    b.putString("content", showlist.get(position).get("content"));
                    b.putString("alerttime",showlist.get(position).get("alerttime"));
                    b.putInt("index", Integer.parseInt(showlist.get(position).get("id")));
                    searchintent.putExtra("android.intent.extra.INTENT", b);
                    startActivity(searchintent);                                //启动转到的显示列表项目Activity
                }
            });
        }
        return false;
    }

    private boolean deleteItem(int position){//删除
        Utils.DateToMillis(list);
        ListView listview = getListView();
        String deleteDatetime = ((HashMap<String, String>)(listview.getItemAtPosition(index))).get("datetime").toString();
        Utils.getList().remove(index);

//		String deleteContent = ((HashMap)(list.getItemAtPosition(index))).get("content").toString();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteUtils sqlite = new SQLiteUtils();
        sqlite.delete(dbHelper, deleteDatetime);//删除操作

        Utils.sort();
        Utils.MillisToDate(list);
        listAdapter.notifyDataSetChanged();                           //更新ListView的数据显示
        return true;
    }

    private void shareItem(int index) {//分享
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "hi,我是zhaochen，来自备忘录分享："+Utils.getItem(index).get("content"));
        startActivity(Intent.createChooser(intent, "分享到"));//启动分享intent
    }

    public void onConfigurationChanged(Configuration newConfig) {//方向
        super.onConfigurationChanged(newConfig);
        ActivityLand.showActivityLand(this);
        finish();
    }


}