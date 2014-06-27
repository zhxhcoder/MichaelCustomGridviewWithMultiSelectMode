package com.michael;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.*;

public class HomeActivity extends Activity implements MultiChoiceModeListener {

    private NoScrollGridView mGridView;
    private NoScrollListView mListView;
    private GridAdapter mGridAdapter;
    private TextView mActionText;
    private TextView deleteView;

    private static final int MENU_SELECT_ALL = 0;
    private static final int MENU_UNSELECT_ALL = MENU_SELECT_ALL + 1;
    private Map<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();

    private List<Integer> mImgIdList;
    private Integer[] mImgIds = new Integer[]{R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4, R.drawable.img_5,
            R.drawable.img_6, R.drawable.img_7, R.drawable.img_8
};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mImgIdList= new ArrayList<Integer>();
        mImgIdList= Arrays.asList(mImgIds);

        mGridView = (NoScrollGridView) findViewById(R.id.gridview);
        mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);// 设置为多选模式
        mGridAdapter = new GridAdapter(this);
        mGridAdapter.setAdapterData(mImgIdList, mSelectMap);
        mGridAdapter.notifyDataSetChanged();

        mGridView.invalidateViews();
        mGridView.setAdapter(mGridAdapter);// 数据适配
        mGridView.setMultiChoiceModeListener(this);// 设置多选模式监听器



        deleteView= (TextView) findViewById(R.id.deleteView);
/*
        //TODO michael 也可以放进去一个特殊的listView
        mListView = (NoScrollListView) findViewById(R.id.listview);
        mListView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);// 设置为多选模式
        mGridAdapter = new GridAdapter(this);
        mGridAdapter.setAdapterData(mImgIds, null);
        mListView.setAdapter(mGridAdapter);// 数据适配*/


        //TODO michael 也可以放到Adapter的getView函数中（但长按事件就被覆盖了）
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO michael
                Toast.makeText(HomeActivity.this,"选中position:"+i,0).show();

            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO michael 不成功
                List<Integer> tempList=new ArrayList<Integer>();
                for (Integer key : mSelectMap.keySet()) {
                    if(!mSelectMap.get(key)){
                        tempList.add(mImgIdList.get(key));
                    }
                }
                mImgIdList=tempList;

                Toast.makeText(HomeActivity.this,"删除成功数："+(95-mImgIdList.size()),0).show();

            }
        });


    }

    /**
     * Override MultiChoiceModeListener start *
     */
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub
        // 得到布局文件的View
        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_layout,
                null);
        mActionText = (TextView) v.findViewById(com.michael.R.id.action_text);
        // 设置显示内容为GridView选中的项目个数
        mActionText.setText(formatString(mGridView.getCheckedItemCount()));
        // 设置动作条的视图
        mode.setCustomView(v);
        // 得到菜单
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub
        /* 初始状态下,如果选中的项数不等于总共的项数,设置"全选"的状态为True */
        menu.getItem(MENU_SELECT_ALL).setEnabled(
                mGridView.getCheckedItemCount() != mGridView.getCount());
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // TODO Auto-generated method stub
        /*
		 * 当点击全选的时候,全选 点击全不选的时候全不选
		 */
        switch (item.getItemId()) {
            case R.id.menu_select:
                for (int i = 0; i < mGridView.getCount(); i++) {
                    mGridView.setItemChecked(i, true);
                    mSelectMap.put(i, true);
                }
                break;
            case R.id.menu_unselect:
                for (int i = 0; i < mGridView.getCount(); i++) {
                    mGridView.setItemChecked(i, false);
                    mSelectMap.clear();
                }
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // TODO Auto-generated method stub
        mGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position,
                                          long id, boolean checked) {
        // TODO Auto-generated method stub
        // 当每个项状态改变的时候的操作
        mActionText.setText(formatString(mGridView.getCheckedItemCount()));
        mSelectMap.put(position, checked);/* 放入选中的集合中 */
        mode.invalidate();
    }

    /**
     * Override MultiChoiceModeListener end *
     */

    private String formatString(int count) {
        return String.format(getString(R.string.selection), count);
    }


}