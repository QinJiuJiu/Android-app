package com.example.memo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.Calendar.CalendarView;
import com.example.memo.Calendar.SlideCalendarActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class edit_item extends AppCompatActivity {
    private boolean[] checkedItems;//记录各列表项的状态
    private String[] items;//各列表项要显示的内容

    private TextView data1,data2,data3;//日期数据显示
    private Button data_base1,data_base2,data_base3;//通过滑动日历选择
    Calendar calendar = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    private int temp;
    private int this_years = calendar.get(Calendar.YEAR);
    private int this_months = calendar.get(Calendar.MONTH)+1;
    private int this_days = calendar.get(Calendar.DAY_OF_MONTH);
    private int flag = 0;
    private int next_years = 0;
    private int next_months = 0;
    private int next_days = 0;

    private String pTime = "";//设置星期

    private CalendarView calendarView1,calendarView2,calendarView3;
    //没有选择时，将会显示的日期，也可以根据系统获取当前时间

    private String years = "2000";
    private String months = "July";
    private String days = "16";

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(edit_item.this, "您单击了【确定】按钮！",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(edit_item.this, "您单击了【取消】按钮！",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RecyclerView mrecyclerView;
//    private MyRecyclerViewAdapter recyclerViewAdapter;
    private List<String> words;
    private RecyclerView.LayoutManager layoutManager;

    private String notes = "None";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        data1 = (TextView)findViewById(R.id.text_schedule);
        data_base1 = (Button)findViewById(R.id.button_schedule);

        data2 = (TextView)findViewById(R.id.text_deadline);
        data_base2 = (Button)findViewById(R.id.button_deadline);

        data3 = (TextView)findViewById(R.id.text_showon);
        data_base3 = (Button)findViewById(R.id.button_showon);

        c.add(Calendar.DAY_OF_MONTH,1);
        next_years = c.get(Calendar.YEAR);
        next_months = c.get(Calendar.MONTH)+1;
        next_days = c.get(Calendar.DAY_OF_MONTH);

        data_base1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击弹出滑轮选择日历控件
                flag = 1;
                myCalendar();
            }
        });

        data_base2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击弹出滑轮选择日历控件
                flag = 2;
                myCalendar();
            }
        });

        data_base3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击弹出滑轮选择日历控件
                flag = 3;
                myCalendar();
            }
        });

        // state单选列表对话框
        Button b_state = (Button) findViewById(R.id.button_state); // 获取“单选列表对话框”按钮
        b_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"None", "TODO", "DONE"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(edit_item.this);
//                builder.setIcon(R.drawable.tools); //设置对话框的图标
                builder.setTitle("Set State"); //设置对话框的标题
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(edit_item.this,
                                "You selected " + items[which], Toast.LENGTH_SHORT).show(); //显示选择结果
                        TextView textView = findViewById(R.id.text_state);
                        textView.setText(items[which]);
                    }
                });
                setPositiveButton(builder);//添加“确定”按钮
                setNegativeButton(builder);//添加“取消”按钮
                builder.create().show(); // 创建对话框并显示
            }
        });

        // priority单选列表对话框
        Button b_priority = (Button) findViewById(R.id.button_priority); // 获取“单选列表对话框”按钮
        b_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"None", "A", "B", "C", "D"};
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_item.this);
//                builder.setIcon(R.drawable.tools); //设置对话框的图标
                builder.setTitle("Set Priority"); //设置对话框的标题
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(edit_item.this,
                                "You selected " + items[which], Toast.LENGTH_SHORT).show(); //显示选择结果
                        TextView textView = findViewById(R.id.text_priority);
                        textView.setText(items[which]);
                    }
                });
                setPositiveButton(builder);//添加“确定”按钮
                setNegativeButton(builder);//添加“取消”按钮
                builder.create().show(); // 创建对话框并显示
            }
        });

//        final TextView t_editnotes = (TextView) findViewById(R.id.tttnotes);
        // notes列表对话框
        Button b_notes = (Button) findViewById(R.id.button_notes); // 获取“单选列表对话框”按钮
        b_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String items = "Noneaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_item.this);
//                builder.setIcon(R.drawable.tools); //设置对话框的图标
                builder.setTitle("Notes"); //设置对话框的标题
                TextView t_editnotes = (TextView) findViewById(R.id.tttnotes);
                builder.setMessage(t_editnotes.getText().toString());
                setPositiveButton(builder);//添加“确定”按钮
                builder.create().show(); // 创建对话框并显示
            }
        });

        // editnotes列表对话框
//        String notes = "Noneaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        Button b_editnotes = (Button) findViewById(R.id.button_editnotes); // 获取“单选列表对话框”按钮
        b_editnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(edit_item.this);
//                builder.setIcon(R.drawable.tools); //设置对话框的图标
                builder.setTitle("Edit Notes"); //设置对话框的标题

                final EditText et = new EditText(edit_item.this);
                TextView t_editnotes = (TextView) findViewById(R.id.tttnotes);
                et.setText(t_editnotes.getText().toString());
                builder.setView(et);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        Toast.makeText(getApplicationContext(), et.getText().toString(),Toast.LENGTH_LONG).show();
                        TextView t_editnotes = (TextView) findViewById(R.id.tttnotes);
                        t_editnotes.setText(et.getText().toString());

                    }
                });
                setNegativeButton(builder);//添加“取消”按钮
//                setPositiveButton(builder);//添加“确定”按钮
                builder.create().show(); // 创建对话框并显示
            }
        });



        Button b_tags = (Button) findViewById(R.id.button_tags); // 获取“单选列表对话框”按钮
        b_tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(edit_item.this);
                LayoutInflater inflater=getLayoutInflater();
                final View view=inflater.inflate(R.layout.tags, null);
                dialog.setContentView(view);
                dialog.show();

                // addtags列表对话
                Button b_addtags = (Button) view.findViewById(R.id.button_addtags);
//                Button b_addtags = (Button) findViewById(R.id.button_addtags); // 获取“单选列表对话框”按钮
                b_addtags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(edit_item.this);
                        builder.setTitle("Add Tags"); //设置对话框的标题

                        final EditText et = new EditText(edit_item.this);
                        builder.setView(et);

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //按下确定键后的事件
                                Toast.makeText(getApplicationContext(), et.getText().toString(),Toast.LENGTH_LONG).show();
                                //加入文件tags


                            }
                        });
                        setNegativeButton(builder);//添加“取消”按钮
                        builder.create().show(); // 创建对话框并显示
                    }
                });


                SearchView searchView = (SearchView)view.findViewById(R.id.searchView);
                final ListView mListView = (ListView)view.findViewById(R.id.listView);
                //设置该搜索框默认是否自动缩小为图标
                searchView.setIconifiedByDefault(false);
                //MainActivity.this :context     listDate:数据源
                words=new ArrayList<>();
                words.add("abc");
                words.add("bbc");
                words.add("cbc");
                String[] items = new String[]{"刘", "非", "凡", "大", "傻","子","啊","啊","啊"};
                final ArrayAdapter adapter = new ArrayAdapter(edit_item.this, android.R.layout.select_dialog_item, items);
                mListView.setAdapter(adapter);
                //true表示listview获得当前焦点的时候，与相应用户输入的匹配符进行比对，筛选出匹配的ListView的列表中的项
                mListView.setTextFilterEnabled(true);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = (String)adapter.getItem(position);
                        TextView textView = findViewById(R.id.text_tags);
                        textView.setText(item);
                        //xxxxxxxxxxxx 其他操作
                        dialog.dismiss();
                    }
                });
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
                {
                    // 当点击搜索按钮时触发该方法
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    // 当搜索内容改变时触发该方法
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (!TextUtils.isEmpty(newText)){
                            //使用此方法搜索会出现一个黑色框框
//                            mListView.setFilterText(newText);
                            //未显示效果采用下面方法 不会出现黑色框
                            adapter.getFilter().filter(newText);
                        }else{
//                            mListView.setFilterText(newText);
                            adapter.getFilter().filter("");
                        }
                        return false;
                    }
                });
            }


        });

        // file列表对话框
        Button b_save = (Button) findViewById(R.id.button_save); // 获取“单选列表对话框”按钮
        b_editnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "mysdcard.txt");
                Log.d("aaa", "file.exists():" + file.exists() + " file.getAbsolutePath():"+ file.getAbsolutePath());
                if (file.exists()) {
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
// Toast.makeText(MainActivity.this, "SD卡目录下创建文件成功...", Toast.LENGTH_LONG).show();
                Log.d("aaa", "SD卡目录下创建文件成功...");

                // 在SD卡目录下的文件，写入内容
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fw.write("我的sdcard内容.....");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
// Toast.makeText(MainActivity.this, "SD卡写入内容完成...",Toast.LENGTH_LONG).show();
                Log.d("aaa", "SD卡写入内容完成...");

                // 读取SD卡文件里面的内容
                FileReader fr = null;
                try {
                    fr = new FileReader("/mnt/sdcard/mysdcard.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(fr);
                String result = null;
                try {
                    result = r.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("aaa", "SD卡文件里面的内容:" + result);


            }
        });


    }

    public void myCalendar(){
        years="2000";
        months="July";
        days="16";

        //初始化对话框             R.style.CalendarDialog 是自定义的弹框主题，在styles设置
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.CalendarDialog);
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
                androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(edit_item.this)
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
                    Field mAlert = androidx.appcompat.app.AlertDialog.class.getDeclaredField("mAlert");
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
                if(flag==1) data1.setText(years + " 年 "+ months + " 月 " + days + " 日 ");
                if(flag==2) data2.setText(years + " 年 "+ months + " 月 " + days + " 日 ");
                if(flag==3) data3.setText(years + " 年 "+ months + " 月 " + days + " 日 ");

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
