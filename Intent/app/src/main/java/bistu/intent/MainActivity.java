package bistu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText input = (EditText) findViewById(R.id.editText) ;
                String msg = input.getText().toString();

//                String msg = "hello,SecondActivity";
//                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
//                intent.putExtra("amsg",meg);
//                startActivity(intent);

                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this, Main2Activity.class);
                intent3.putExtra("amsg",msg);
                startActivity(intent3);
            }
        });


    }
}
