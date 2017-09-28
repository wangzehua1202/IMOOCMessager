package com.xingsu.italker.factory.presenter.account;

import com.xingsu.italker.common.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {

    }

    @Override
    public boolean checkMobile(String phone) {
        return false;
    }

}
