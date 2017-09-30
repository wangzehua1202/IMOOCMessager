package com.xingsu.italker.factory.presenter.user;

import com.xingsu.italker.common.factory.presenter.BaseContract;

/**
 * 更新用户信息的基本的契约
 * Created by Administrator on 2017/9/30 0030.
 */

public interface UpdateInfoContract {
    interface Presenter extends BaseContract.Presenter{
        void update(String photoFilePath, String desc, boolean isMan);
    }

    interface View extends BaseContract.View<Presenter>{
        //回调成功
        void updateSucceed();
    }
}
