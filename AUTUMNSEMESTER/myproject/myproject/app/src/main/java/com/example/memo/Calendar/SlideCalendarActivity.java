package com.example.memo.Calendar;

import android.content.DialogInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memo.R;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


//弹出一个弹框，通过滑动选择日历日期
public class SlideCalendarActivity extends AppCompatActivity {

    private TextView data;//日期数据显示
    private Button data_base;//通过滑动日历选择
    Calendar calendar = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    private int temp;
    private int this_years = calendar.get(Calendar.YEAR);
    private int this_months = calendar.get(Calendar.MONTH)+1;
    private int this_days = calendar.get(Calendar.DAY_OF_MONTH);

    private int next_years = 0;
    private int next_months = 0;
    private int next_days = 0;

    private String pTime = "";//设置星期

    private CalendarView calendarView1,calendarView2,calendarView3;
    //没有选择时，将会显示的日期，也可以根据系统获取当前时间

    private String years = "2000";
    private String months = "July";
    private String days = "16";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);
        data = (TextView)findViewById(R.id.data);
        data_base = (Button)findViewById(R.id.button_schedule);

        c.add(Calendar.DAY_OF_MONTH,1);
        next_years = c.get(Calendar.YEAR);
        next_months = c.get(Calendar.MONTH)+1;
        next_days = c.get(Calendar.DAY_OF_MONTH);

        data_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击弹出滑轮选择日历控件
                myCalendar();
            }
        });
    }

    public void myCalendar(){
        years="2000";
        months="July";
        days="16";

        //初始化对话框             R.style.CalendarDialog 是自定义的弹框主题，在styles设置
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CalendarDialog);
        //初始化自定义布局参数
        LayoutInflater layoutInflater = getLayoutInflater();
        //加载布局
        View customLayout = layoutInflater.inflate(R.layout.view_slide_calendar, (ViewGroup) findViewById(R.id.customDialog));
        //为对话框设置视图
        builder.setView(customLayout);

        final Button today = customLayout.findViewById(R.id.today);
        final Button tomorrow = customLayout.findViewById(R.id.tomorrow);
        final Button p_1d = customLayout.findViewById(R.id.p_1d);
        final Button p_7d = customLayout.findViewById(R.id.p_7d);
        final Button m_1d = customLayout.findViewById(R.id.m_1d);
        final Button m_7d = customLayout.findViewById(R.id.m_7d);
        final Button rep = customLayout.findViewById(R.id.repeat);

        //加载年月日的三个 CalendarView 的 id
        calendarView1 = (CalendarView) customLayout.findViewById(R.id.year);
        calendarView2 = (CalendarView) customLayout.findViewById(R.id.month);
        calendarView3 = (CalendarView) customLayout.findViewById(R.id.day);

        final TextView week = customLayout.findViewById(R.id.week);

        //定义滚动选择器的数据项（年月日的）
        final ArrayList<String> gradeYear = new ArrayList<>();
        final ArrayList<String> gradeMonth = new ArrayList<>();
        final ArrayList<String> gradeDay = new ArrayList<>();

        //为数据项赋值
        int thisYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new java.util.Date()));
        for(int i=1980;i<=thisYear;i++) //从1980到今年
            gradeYear.add(i + "");

        for(int i=1;i<=31;i++)           // 1日到31日
            gradeDay.add(i + "");

        for(int i=1;i<=12;i++)            // 1月到12月
        {
            switch (i)
            {
                case 1:
                    gradeMonth.add("January");
                    break;
                case 2:
                    gradeMonth.add("February");
                    break;
                case 3:
                    gradeMonth.add("March");
                    break;
                case 4:
                    gradeMonth.add("April");
                    break;
                case 5:
                    gradeMonth.add("May");
                    break;
                case 6:
                    gradeMonth.add("June");
                    break;
                case 7:
                    gradeMonth.add("July");
                    break;
                case 8:
                    gradeMonth.add("August");
                    break;
                case 9:
                    gradeMonth.add("September");
                    break;
                case 10:
                    gradeMonth.add("October");
                    break;
                case 11:
                    gradeMonth.add("November");
                    break;
                case 12:
                    gradeMonth.add("December");
                    break;
            }
        }


        //为滚动选择器设置数据
        calendarView1.setData(gradeYear);
        calendarView2.setData(gradeMonth);
        calendarView3.setData(gradeDay);

        //滚动选择事件
        calendarView1.setOnSelectListener(new CalendarView.onSelectListener() {
            @Override
            public void onSelect(String data) {
                years = data;
                //设置星期

                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day);
            }
        });
        calendarView2.setOnSelectListener(new CalendarView.onSelectListener() {
            @Override
            public void onSelect(String data) {
                months = data;
                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day);
            }
        });
        calendarView3.setOnSelectListener(new CalendarView.onSelectListener() {
            @Override
            public void onSelect(String data) {
                days = data;
                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day);
            }
        });


        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把日期移到今天

                //年
                int year_dis = this_years - Integer.parseInt(gradeYear.get(gradeYear.size()/2));
                if(year_dis>0)
                {
                    for(int i=0;i<year_dis;i++)
                    {
                        String head = gradeYear.get(0);
                        gradeYear.remove(0);
                        gradeYear.add(head);
                    }
                    calendarView1.setData(gradeYear);
                    years=gradeYear.get(gradeYear.size()/2);
                }
                else if(year_dis<0)
                {
                    for(int i=0;i<-year_dis;i++)
                    {
                        String tail = gradeYear.get(gradeYear.size() - 1);
                        gradeYear.remove(gradeYear.size() - 1);
                        gradeYear.add(0, tail);
                    }
                    calendarView1.setData(gradeYear);
                    years=gradeYear.get(gradeYear.size()/2);
                }

                //月

                int month_dis = this_months - month2number_int(gradeMonth.get(5));

                if(month_dis>0)
                {
                    for(int i=0;i<month_dis;i++)
                    {
                        String head = gradeMonth.get(0);
                        gradeMonth.remove(0);
                        gradeMonth.add(head);
                    }
                    calendarView2.setData(gradeMonth);
                    months=gradeMonth.get(5);
                }
                else if(month_dis<0)
                {
                    for(int i=0;i<-month_dis;i++)
                    {
                        String tail = gradeMonth.get(gradeMonth.size() - 1);
                        gradeMonth.remove(gradeMonth.size() - 1);
                        gradeMonth.add(0, tail);
                    }
                    calendarView2.setData(gradeMonth);
                    months=gradeMonth.get(5);
                }

                //日
                int day_dis = this_days - Integer.parseInt(gradeDay.get(15));
                if(day_dis>0)
                {
                    for(int i=0;i<day_dis;i++)
                    {
                        String head = gradeDay.get(0);
                        gradeDay.remove(0);
                        gradeDay.add(head);
                    }
                    calendarView3.setData(gradeDay);
                    days=gradeDay.get(15);
                }
                else if(day_dis<0)
                {
                    for(int i=0;i<-day_dis;i++)
                    {
                        String tail = gradeDay.get(gradeDay.size() - 1);
                        gradeDay.remove(gradeDay.size() - 1);
                        gradeDay.add(0, tail);
                    }
                    calendarView3.setData(gradeDay);
                    days=gradeDay.get(15);
                }
                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day+"(Today)");

            }
        });

        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把日期移到今天

                //年
                int year_dis = next_years - Integer.parseInt(gradeYear.get(gradeYear.size()/2));
                if(year_dis>0)
                {
                    for(int i=0;i<year_dis;i++)
                    {
                        String head = gradeYear.get(0);
                        gradeYear.remove(0);
                        gradeYear.add(head);
                    }
                    calendarView1.setData(gradeYear);
                    years=gradeYear.get(gradeYear.size()/2);
                }
                else if(year_dis<0)
                {
                    for(int i=0;i<-year_dis;i++)
                    {
                        String tail = gradeYear.get(gradeYear.size() - 1);
                        gradeYear.remove(gradeYear.size() - 1);
                        gradeYear.add(0, tail);
                    }
                    calendarView1.setData(gradeYear);
                    years=gradeYear.get(gradeYear.size()/2);
                }

                //月
                int month_dis = next_months - month2number_int(gradeMonth.get(5));

                if(month_dis>0)
                {
                    for(int i=0;i<month_dis;i++)
                    {
                        String head = gradeMonth.get(0);
                        gradeMonth.remove(0);
                        gradeMonth.add(head);
                    }
                    calendarView2.setData(gradeMonth);
                    months=gradeMonth.get(5);
                }
                else if(month_dis<0)
                {
                    for(int i=0;i<-month_dis;i++)
                    {
                        String tail = gradeMonth.get(gradeMonth.size() - 1);
                        gradeMonth.remove(gradeMonth.size() - 1);
                        gradeMonth.add(0, tail);
                    }
                    calendarView2.setData(gradeMonth);
                    months=gradeMonth.get(5);
                }

                //日
                int day_dis = next_days - Integer.parseInt(gradeDay.get(15));
                if(day_dis>0)
                {
                    for(int i=0;i<day_dis;i++)
                    {
                        String head = gradeDay.get(0);
                        gradeDay.remove(0);
                        gradeDay.add(head);
                    }
                    calendarView3.setData(gradeDay);
                    days=gradeDay.get(15);
                }
                else if(day_dis<0)
                {
                    for(int i=0;i<-day_dis;i++)
                    {
                        String tail = gradeDay.get(gradeDay.size() - 1);
                        gradeDay.remove(gradeDay.size() - 1);
                        gradeDay.add(0, tail);
                    }
                    calendarView3.setData(gradeDay);
                    days=gradeDay.get(15);
                }

                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day+"(Tomorrow)");

            }
        });

        p_1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head = gradeDay.get(0);
                gradeDay.remove(0);
                gradeDay.add(head);
                calendarView3.setData(gradeDay);
                days=gradeDay.get(15);
                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day);
            }
        });

        p_7d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++)
                {
                    String head = gradeDay.get(0);
                    gradeDay.remove(0);
                    gradeDay.add(head);
                }

                calendarView3.setData(gradeDay);
                days=gradeDay.get(15);
                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day);
            }
        });

        m_1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tail = gradeDay.get(gradeDay.size() - 1);
                gradeDay.remove(gradeDay.size() - 1);
                gradeDay.add(0, tail);
                calendarView3.setData(gradeDay);
                days=gradeDay.get(15);
                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day);
            }
        });

        m_7d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++)
                {
                    String tail = gradeDay.get(gradeDay.size() - 1);
                    gradeDay.remove(gradeDay.size() - 1);
                    gradeDay.add(0, tail);
                }
                calendarView3.setData(gradeDay);
                days=gradeDay.get(15);
                pTime = years + "-" + month2number_string(months) + "-";
                if(Integer.parseInt(days)<=9)
                {
                    pTime = pTime+'0'+days;
                }
                else
                {
                    pTime = pTime+days;
                }

                String week_day = getWeek(pTime);
                week.setText(week_day);
            }

        });

        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 通过builder 构建器来构造
                AlertDialog dialog = new AlertDialog.Builder(SlideCalendarActivity.this)
                        .setTitle("Repeat time")  //设置标题
                        .setIcon(R.mipmap.ic_launcher) //设置图标
                        .setSingleChoiceItems(new String[]{"1d","1w","1m","1y"}, 0,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }
                        )
                        .setPositiveButton("确定",null)   //添加“确定”按钮
                        .setNegativeButton("取消",null)   //添加“取消”按钮
                        .show();  //显示对话框
                // 最后一步 一定要记得 和Toast 一样 show出来
                try {
                    Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                    mAlert.setAccessible(true);
                    Object mAlertController = mAlert.get(dialog);
                    Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                    mMessage.setAccessible(true);
                    TextView mMessageView = (TextView) mMessage.get(mAlertController);
                    mMessageView.setTextColor(Color.BLUE);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }

        });

        //对话框的确定按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.setText(years + " 年 "+ months + " 月 " + days + " 日 ");

            }
        });
        //对话框的取消按钮
        builder.setNegativeButton("取消", null);
        //显示对话框
        builder.show();
    }

    private String month2number_string(String month_s)
    {
        String month;
        switch (month_s)
        {
            case "January": month="01";break;
            case "February": month="02";break;
            case "March": month="03";break;
            case "April": month="04";break;
            case "May": month="05";break;
            case "June": month="06";break;
            case "July": month="07";break;
            case "August": month="08";break;
            case "September": month="09";break;
            case "October": month="10";break;
            case "November": month="11";break;
            case "December": month="12";break;
            default:month="";break;
        }
        return month;
    }

    private int month2number_int(String month_s)
    {
        int month;
        switch (month_s)
        {
            case "January": month=1;break;
            case "February": month=2;break;
            case "March": month=3;break;
            case "April": month=4;break;
            case "May": month=5;break;
            case "June": month=6;break;
            case "July": month=7;break;
            case "August": month=8;break;
            case "September": month=9;break;
            case "October": month=10;break;
            case "November": month=11;break;
            case "December": month=12;break;
            default:month=0;break;
        }
        return month;
    }

    private String getWeek(String pTime) {


        String Week = "";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "Sunday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "Monday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "Tuesday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "Wednesday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "Thursday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "Friday";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "Saturday";
        }
        return Week;
    }
}
