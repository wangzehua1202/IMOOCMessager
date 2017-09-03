package com.xingsu.italker.common.widget.recycler;

/**
 * Created by 王泽华 on 2017/9/3.
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder );
}
