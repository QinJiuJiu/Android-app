package com.example.memo.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.memo.Agenda.FirstAdapter;
import com.example.memo.Agenda.FirstEntity;
import com.example.memo.Agenda.SecondEntity;
import com.example.memo.R;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {

    ListView lv_first;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);;
        super.onCreate(savedInstanceState);

        List<FirstEntity> list = new ArrayList<FirstEntity>();
        for(int idx = 0;idx < 10;idx++){
            FirstEntity fe = new FirstEntity();
            fe.setUserName("idx_"+idx);
            List<SecondEntity> lse = new ArrayList<>();
            for(int i = 0;i<4;i++){
                SecondEntity se = new SecondEntity();
                se.setParam1("todotodotodo");
                se.setParam2("i_" + i);
                se.setParam3("TODO");
                lse.add(se);
            }
            fe.setSecondList(lse);
            list.add(fe);
        }

        lv_first = (ListView) view.findViewById(R.id.list_todo);
        final FirstAdapter fa = new FirstAdapter(getActivity(),list);
        lv_first.setAdapter(fa);
        fa.notifyDataSetChanged();

        SearchView searchView = (SearchView)view.findViewById(R.id.todo_searchView);
//        ListView mListView = (ListView)findViewById(R.id.list_agenda);
        //设置该搜索框默认是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
        //MainActivity.this :context     listDate:数据源
//        final ArrayAdapter adapter = new ArrayAdapter(AgendaActivity.this, android.R.layout.select_dialog_item, list);
//        mListView.setAdapter(adapter);
        //true表示listview获得当前焦点的时候，与相应用户输入的匹配符进行比对，筛选出匹配的ListView的列表中的项
        lv_first.setTextFilterEnabled(true);
        lv_first.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = (String)fa.getItem(position);
//                TextView textView = findViewById(R.id.text_tags);
//                textView.setText(item);
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
                    lv_first.setFilterText(newText);
                    //未显示效果采用下面方法 不会出现黑色框
//                    fa.getFilter().filter(newText);
                }else{
                    lv_first.setFilterText(newText);
//                    fa.getFilter().filter("");
                }
                return false;
            }
        });
        return view;
    }
}
