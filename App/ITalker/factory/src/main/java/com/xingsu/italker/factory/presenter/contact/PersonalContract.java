package com.xingsu.italker.factory.presenter.contact;

import com.xingsu.italker.common.factory.presenter.BaseContract;
import com.xingsu.italker.factory.model.db.User;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter{
        //获取用户信息
        User getUserPersonal();
    }

    interface View extends BaseContract.View<Presenter>{
        String getUserId();
        //加载数据完成
        void onLoadDone(User user);

        //是否发起聊天
        void allowSayHello(boolean isAllow);

        //设置关注状态
        void setFollowStatus(boolean isFollow);

    }
}
