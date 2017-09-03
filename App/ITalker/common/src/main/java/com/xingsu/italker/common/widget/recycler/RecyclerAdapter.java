package com.xingsu.italker.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王泽华 on 2017/9/3.
 */

public class RecyclerAdapter<Data> extends RecyclerView.Adapter {
    private final List<Data> mDataList = new ArrayList();
    /**
     * 创建viewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * 绑定viewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     holder     * 得到个数
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
