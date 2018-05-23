package bistu.caculator2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button2 = (Button) findViewById(R.id.button2);
        button8 = (Button) findViewById(R.id.button8);
        button10 = (Button) findViewById(R.id.button10);
        button16 = (Button) findViewById(R.id.button16);
        editText = (EditText) findViewById(R.id.editText);
        textView=(TextView) findViewById(R.id.textView);






        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String str=editText.getText().toString();
                num=Integer.valueOf(str);
               result=Integer.toBinaryString(num);
                textView.setText(result);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String str=editText.getText().toString();
                num=Integer.valueOf(str);
                 result=Integer.toOctalString(num);
                textView.setText(result);
            }
        });

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String str=editText.getText().toString();
                num=Integer.valueOf(str);
                result=Integer.toHexString(num);
                textView.setText(result);

            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String str=editText.getText().toString();
                num=Integer.valueOf(str);
                //BigInteger numm=new BigInteger(Integer.toString(num));
                result=Integer.valueOf(toD(str, 2)).toString();


                textView.setText(result);
            }
        });




    }

    private Button button2,button8,button10,button16;
    private EditText editText;
    private TextView textView;
    String result;
    Integer num;
    //String num=editText.getText().toString();

    // 任意进制数转为十进制数
    public String toD(String a, int b) {//---------------------------a为16进制，b=16；三
        int r = 0;
        for (int i = 0; i < a.length(); i++) {
            r = (int) (r + formatting(a.substring(i, i + 1))
                    * Math.pow(b, a.length() - i - 1));
        }
        return String.valueOf(r);
    }

    // 将十六进制中的字母转为对应的数字
    public int formatting(String a) {
        int i = 0;
        for (int u = 0; u < 10; u++) {
            if (a.equals(String.valueOf(u))) {
                i = u;
            }
        }
        if (a.equals("a")) {
            i = 10;
        }
        if (a.equals("b")) {
            i = 11;
        }
        if (a.equals("c")) {
            i = 12;
        }
        if (a.equals("d")) {
            i = 13;
        }
        if (a.equals("e")) {
            i = 14;
        }
        if (a.equals("f")) {
            i = 15;
        }
        return i;
    }





}
