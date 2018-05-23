package bistu.sq2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;

public class Main2Activity extends AppCompatActivity {

    private Helper helper;
    List<St> stList;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        helper=new Helper(this,"student.db",null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        stList=new ArrayList<St>();
        ListView lv=(ListView) findViewById(R.id.lv);
        Query();
        myAdapter = new MyAdapter(this);
        lv.setAdapter(myAdapter);
    }

    public void Query(){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query("stu",null,null,null,null,null,null);

        while (cursor.moveToNext()){
            String num=cursor.getString(cursor.getColumnIndex("num"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            St st=new St(num,name);
            stList.add(st);
        }

    }

    class ViewHolder {
        private TextView num,name;
    }

    class MyAdapter extends BaseAdapter{

        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }



        //获取集合中有多少条元素,由系统调用
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return stList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            St s=stList.get(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.listitem_layout, null);
                viewHolder.num = (TextView) convertView
                        .findViewById(R.id.num);
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //向TextView中插入数据
            viewHolder.num.setText(s.getNum());
            viewHolder.name.setText(s.getName());

            return convertView;

        }

    }

}
