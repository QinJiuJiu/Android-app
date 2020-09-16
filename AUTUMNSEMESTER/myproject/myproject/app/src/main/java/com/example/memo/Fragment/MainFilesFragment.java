package com.example.memo.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.memo.Files.FilesAdapter;
import com.example.memo.Files.FilesItem;
import com.example.memo.Files.TreeAdapter;
import com.example.memo.Files.TreePoint;
import com.example.memo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFilesFragment extends Fragment {

    private Button trans_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mainfiles, container, false);


        // addtags列表对话

        SearchView searchView = (SearchView) view.findViewById(R.id.search_view);
        ListView mListView = (ListView) view.findViewById(R.id.mf_listView);
        //设置该搜索框默认是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
        //MainActivity.this :context     listDate:数据源
        ArrayList<FilesItem> item = new ArrayList<>();
        FilesItem temp;
        temp = new FilesItem("File1","time1");
        item.add(temp);
        temp = new FilesItem("File2","time2");
        item.add(temp);
        temp = new FilesItem("File3","time3");
        item.add(temp);
        final FilesAdapter adapter = new FilesAdapter(getActivity(),item);
        mListView.setAdapter(adapter);
        //true表示listview获得当前焦点的时候，与相应用户输入的匹配符进行比对，筛选出匹配的ListView的列表中的项
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) adapter.getItem(position);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new FilesFragment(), null)
                        .addToBackStack(null)
                        .commit();

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    //使用此方法搜索会出现一个黑色框框
                    //mListView.setFilterText(newText);
                    //未显示效果采用下面方法 不会出现黑色框
                    adapter.getFilter().filter(newText);
                } else {
                    //mListView.setFilterText(newText);
                    adapter.getFilter().filter("");
                }
                return false;
            }
        });


//        trans_btn = view.findViewById(R.id.trans);
//        trans_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frameLayout, new FilesFragment(), null)
//                        .addToBackStack(null)
//                        .commit();
//
//            }
//        });
        return view;
    }


}
