package com.xingsu.italker.common.common.app;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.xingsu.italker.common.R;
import com.xingsu.italker.common.factory.presenter.BaseContract;

/**
 * Created by 王泽华 on 2017/10/15.
 */

public abstract class PresenterToolbarActity<Presenter extends BaseContract.Presenter>
        extends ToolbarActity implements BaseContract.View<Presenter>{
    protected Presenter mPresenter;

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面关闭时进行销毁
        if(mPresenter != null)
            mPresenter.destroy();
    }

    /**
     * 初始化Presenter
     * @return Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void showError(int str) {
        // 显示错误,优先使用占位布局
        if(mPlaceHolderView != null){
            mPlaceHolderView.triggerError(str);
        }
        else {
            // 显示错误
            Application.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        if(mPlaceHolderView != null){
            mPlaceHolderView.triggerLoading();
        }
    }

    protected void hideLoading(){
        if(mPlaceHolderView != null){
            mPlaceHolderView.triggerOk();
        }
    }


    @Override
    public void setPresenter(Presenter presenter) {
        // View中赋值Presenter
        mPresenter = presenter;
    }
}
