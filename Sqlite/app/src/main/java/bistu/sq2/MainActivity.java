package bistu.sq2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText num,name;
    private Button add,show;
    private Helper helper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = (EditText) findViewById(R.id.num);
        name = (EditText) findViewById(R.id.name);
        add = (Button) findViewById(R.id.add);
        show = (Button) findViewById(R.id.show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String nums = num.getText() + "";
                String names = name.getText() + "";
                if (nums.equals(null) || nums == ""
                        || names.equals(null) || names == "") {
                    Toast.makeText(MainActivity.this, "学号或姓名不得为空！",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dbInsert(nums,names);
                    Toast.makeText(
                            MainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void dbInsert(String num, String name) {

        helper=new Helper(MainActivity.this,null,null,0);
        database = helper.getWritableDatabase();// 以读写方法打开数据库，不仅仅是写，getReadableDatabase()是只读
        String sql = "insert into stu(num,name) values (?,?)";
        // 传递过来的username与password分别按顺序替换上面sql语句的两个?，自动转换类型，下同，不再赘述
        Object bindArgs[] = new Object[] { num, name };
        // 执行这条无返回值的sql语句
        database.execSQL(sql, bindArgs);
    }
}
