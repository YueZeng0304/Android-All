package com.example.joe.memorandum;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityLand extends FragmentActivity {//横屏碎片

    private Fragment1 f=new Fragment1();
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(ActivityLand.this, MainActivity.class);
            startActivity(intent);
        }

        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.add(R.id.ll_content,f);
        ft.commit();

    }

    public static void showActivityLand(Activity activity) {
        Intent intent = new Intent(activity, ActivityLand.class);
        activity.startActivity(intent);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Intent intent = new Intent(ActivityLand.this,MainActivity.class);
startActivity(intent);
        finish();
    }

}
