package com.example.joe.memorandum;

/**
 * Created by joe on 2017/7/1.
 */


import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class TimeSetDialog extends Dialog{

    Button dateSetButton,positiveButton,negativeButton;
    TimePicker timePicker;
    Calendar calendar;
    String date,alerttime=null;
    private TimeSetDialog timeSetDialog = null;

    //初始化时间设置
    private void init(){
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateSetButton.setText(Utils.toDateString(calendar));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }
    public TimeSetDialog(Context context) {
        super(context);
        setContentView(R.layout.timeset_view);

        timeSetDialog = this;
        this.setTitle("设置时间提醒我");
//各组件
        calendar = Calendar.getInstance();
        timePicker = (TimePicker)findViewById(R.id.timePicker);//设置时间
        dateSetButton = (Button)findViewById(R.id.dateButton);
        positiveButton = (Button)findViewById(R.id.positiveButton);
        negativeButton = (Button)findViewById(R.id.negativeButton);

        init();

        dateSetButton.setOnClickListener(new View.OnClickListener() {//设置日期，跳出对话框
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //设置日期
                        calendar.set(year, monthOfYear, dayOfMonth);
                        date = Utils.toDateString(calendar);
                        dateSetButton.setText(date);//显示设置的日期
                    }}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        positiveButton.setOnClickListener(new View.OnClickListener() {//设定
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                alerttime = calendar.getTimeInMillis()+"";
                timeSetDialog.cancel();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                timeSetDialog.cancel();
            }
        });
    }
}
