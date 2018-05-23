package bistu.listview;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ListActivity {

            private String[] mListTitle = { "1","2"};
            private String[] mListStr = { "用户名字：曾越\n班级：计科1506\n学号：2015011774\n性别：女","用户名字：周建亭\n班级：计科1505\n学号：2015011774\n性别：男"};
            ListView mListView = null;
            ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                mListView = getListView();

                int lengh = mListTitle.length;
                for(int i =0; i < lengh; i++) {
                    Map<String,Object> item = new HashMap<String,Object>();
                    item.put("title", mListTitle[i]);
                    item.put("text", mListStr[i]);
                    mData.add(item);
                }
                SimpleAdapter adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,
                        new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
                setListAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                            long id) {
                        Toast.makeText(MainActivity.this,"您选择了标题：" + mListTitle[position] + "内容："+mListStr[position], Toast.LENGTH_LONG).show();
                    }
                });
                super.onCreate(savedInstanceState);
            }
        }




