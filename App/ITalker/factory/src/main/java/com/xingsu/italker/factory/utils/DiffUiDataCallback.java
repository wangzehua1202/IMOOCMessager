package com.xingsu.italker.factory.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiffer<T>> extends DiffUtil.Callback{
    private List<T> mOldData,mNewList;

    public DiffUiDataCallback(List<T> mOldData, List<T> mNewList) {
        this.mOldData = mOldData;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        //旧的数据大小
        return mOldData.size();
    }

    @Override
    public int getNewListSize() {
        //新的数据大小
        return mNewList.size();
    }

    //两个类是否是同一个东西，比如id相等的User
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldData.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        return beanNew.isSame(beanOld);
    }

    //在经过相等判断后，进一步判断是否有数据更改
    //比如，同一个用户的两个不同的实例，其中的name字段不同
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldData.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        return beanNew.isUiContentSame(beanOld);
    }

    //进行比较的数据类型
    //泛型的目的，和一个你这个类型的数据比较
    public interface UiDataDiffer<T>{
        //传递一个旧的数据，是否是同一个数据
        boolean isSame(T old);

        //和旧的数据对比，内容是否相同
        boolean isUiContentSame(T old);
    }
}
