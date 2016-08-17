package com.example.myapplication.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myapplication.R;
import com.example.myapplication.adapter.HistoryAdapter;
import com.example.myapplication.bean.ExpBean;
import com.example.myapplication.dao.HistoryInfoDao;
import com.example.myapplication.view.NoScrollViewPager;

import java.util.Collections;
import java.util.List;

;

/**
 * Created by 59427 on 2016/6/24.
 */
public class HistoryFragment extends Fragment {
    private SwipeMenuListView listView;
    private NoScrollViewPager vp;
    List<ExpBean> beanList;
    private HistoryInfoDao dao;
    private View view;
    private HistoryAdapter adapter;
    private RelativeLayout default_layout;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        initData();
        addDelete();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dao = new HistoryInfoDao(getActivity());
        beanList = dao.queryAllExpBean();
        if (beanList.size() > 0) {
            Collections.reverse(beanList);
            listView.setVisibility(View.VISIBLE);
            default_layout.setVisibility(View.GONE);
            adapter = new HistoryAdapter(beanList, getActivity());
            listView.setAdapter(adapter);
        }else {
            listView.setVisibility(View.GONE);
            default_layout.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 左滑删除数据
     */
    private void addDelete() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                deleteItem.setBackground(new ColorDrawable(Color.argb(255, 247, 57, 56)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.argb(0xff, 0xff, 0xff, 0xff));
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                String expName = beanList.get(position).getExpName();
                switch (index) {
                    case 0:
                        dao.delete(expName);
                        beanList.remove(position);
                        adapter.notifyDataSetChanged();
                        if (beanList.size()==0){
                            listView.setVisibility(View.GONE);
                            default_layout.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });
    }

    public void initData() {
        listView = (SwipeMenuListView) view.findViewById(R.id.history);
        default_layout = (RelativeLayout) view.findViewById(R.id.default_layout);
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
