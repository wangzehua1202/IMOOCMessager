package com.xingsu.italker.common.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingsu.italker.common.common.widget.convention.PlaceHolderView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 王泽华 on 2017/9/2.
 */

public abstract class Fragment extends android.support.v4.app.Fragment {
    protected View mRoot;
    protected Unbinder mRootUnBinder;
    protected PlaceHolderView mPlaceHolderView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //初始化参数
        initArgs(getArguments());
    }

    /**
     * 界面初始化
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRoot == null) {
            int layId = getContentLayoutId();
            //初始化当前的根布局，但是不在创建时就添加到container里
            View root = inflater.inflate(layId, container, false);
            initWidget(root);
            mRoot = root;
        }else{
            //重新初始化fragment，有可能mRoot还没有被回收
            if(mRoot.getParent() != null){
                //把当前Root从其父控件中移除
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }

        //return mRoot会在Fragment内部调用的时候添加到container
        return mRoot;
    }

    /**
     * 当界面初始化完成之后
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当View创建完成后初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     */
    protected void initArgs(Bundle bundle){

    }

    /**
     * 得到当前界面的资源文件Id
     * @return 资源文件Id
     */
    @LayoutRes
    protected abstract int getContentLayoutId();

    /**
     *  初始化控件
     */
    protected void initWidget(View root){
        mRootUnBinder = ButterKnife.bind(this,root);
    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }

    /**
     * 返回按键触发时调用
     * @return 返回true表示我已经处理返回逻辑，Activity不用自己finish
     * 返回false表示我没有处理逻辑，Activity走自己的逻辑
     */
    public boolean onBackPressed(){
        return false;
    }

    /**
     * 设置占位布局
     * @param placeHolderView 继承了占位布局规范的View
     */
    public void setmPlaceHolderView(PlaceHolderView placeHolderView){
        this.mPlaceHolderView = placeHolderView;
    }
}
