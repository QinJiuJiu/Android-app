package com.example.memo;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.example.memo.Calendar.SlideCalendarActivity;
import com.example.memo.Fragment.AgendaFragment;
import com.example.memo.Fragment.FilesFragment;
import com.example.memo.Fragment.MainFilesFragment;
import com.example.memo.Fragment.SettingsFragment;
import com.example.memo.Fragment.TodoFragment;
import com.example.memo.utils.BottomNavigationViewHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends TitleActivity {
    private FloatingActionButton calendar;//自定义消息框
    private Toolbar mToolbar;
    private int lastIndex;
    List<Fragment> mFragments;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////
        init();
        initData();
        initBottomNavigation();

        View view = this.getLayoutInflater().inflate(R.layout.fragment_mainfiles, null);
        ////////////
//        showBackwardView(true);
//        showForwardView(true);
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    ///////////////////
    public void init(){
        calendar = findViewById(R.id.fab);
    }
    public void initData(){
        calendar.setOnClickListener(this);

        mFragments = new ArrayList<>();
        mFragments.add(new AgendaFragment());
        mFragments.add(new TodoFragment());
        mFragments.add(new MainFilesFragment());
        mFragments.add(new FilesFragment());
        mFragments.add(new SettingsFragment());
        // 初始化展示MessageFragment
        setFragmentPosition(0);
    }
    //////////////////////
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fab://滚动日历
                startActivity(new Intent(MainActivity.this, edit_item.class));
                break;
        }
    }


    public void initBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.nav_view);

        // 解决当item大于三个时，非平均布局问题
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        setTitle("Agenda");
//                        setFragmentPosition(0);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, new AgendaFragment(), null)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.navigation_dashboard:
                        setTitle("Todo");
//                        setFragmentPosition(1);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, new TodoFragment(), null)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.navigation_notifications:
                        setTitle("Files");
//                        setFragmentPosition(2);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, new MainFilesFragment(), null)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.navigation_mine:
                        setTitle("Settings");
//                        setFragmentPosition(4);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, new SettingsFragment(), null)
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:
                        break;
                }
                // 这里注意返回true,否则点击失效
                return true;
            }
        });
    }


    private void setFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.frameLayout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }
}
