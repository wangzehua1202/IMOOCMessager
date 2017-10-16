package com.xingsu.italker.factory.presenter.contact;

import com.xingsu.italker.common.factory.presenter.BaseContract;
import com.xingsu.italker.factory.model.card.UserCard;

/**
 * 关注的接口定义
 * Created by 王泽华 on 2017/10/16.
 */

public interface FollowContract {
    //任务调度者
    interface Presenter extends BaseContract.Presenter{
        //关注一个人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter>{
        //成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}
