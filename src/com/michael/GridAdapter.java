package com.michael;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by michael on 2014/6/27.
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private int[] mImgIds;
    private Map<Integer, Boolean> mSelectMap;

    public GridAdapter(Context ctx) {
        mContext = ctx;
    }

    public void setAdapterData(int[] mImgIds, Map<Integer, Boolean> mSelectMap) {
        this.mImgIds = mImgIds;
        this.mSelectMap = mSelectMap;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mImgIds.length;
    }

    @Override
    public Integer getItem(int position) {
        // TODO Auto-generated method stub
        return Integer.valueOf(mImgIds[position]);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public String getStrItem(int position) {
        // TODO Auto-generated method stub
        return String.valueOf(mImgIds[position]);
    }


    @SuppressWarnings("deprecation")
    @Override
        /* 得到View */
    public View getView(final int position, View convertView, ViewGroup parent) {
        GridItem item;
        if (convertView == null) {
            item = new GridItem(mContext);
            item.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,
                    AbsListView.LayoutParams.FILL_PARENT));
        } else {
            item = (GridItem) convertView;
        }
        item.setImgResId(getItem(position));
        item.setChecked(mSelectMap.get(position) == null ? false
                : mSelectMap.get(position));
        //TODO michael
        item.setTitleText(getStrItem(position));


/*
        //TODO 放这里长按多选就无法进行
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "选中position:" + position, 0).show();

            }
        });
*/






        return item;
    }
}