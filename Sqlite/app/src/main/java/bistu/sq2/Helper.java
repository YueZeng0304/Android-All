package bistu.sq2;

/**
 * Created by Administrator on 2018/5/9.
 */
//创建数据库
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class Helper extends SQLiteOpenHelper{

    public Helper(Context context,String name,CursorFactory factory,int version){
        super(context,"student.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase) {
        String sql=
                "create table if not exists stu("+
                        "num VARCHAR(255),"+
                        "name VARCHAR(255) "+
                        ")";//如果初次运行，建立一张t_user表，建表的时候注意，自增是AUTOINCREMENT，而不是mysql的AUTO_INCREMENT
        sqliteDatabase.execSQL(sql);

    }

    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        //这里是更新数据库版本时所触发的方法
    }

}
