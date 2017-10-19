package com.xingsu.italker.factory.data.user;

import com.xingsu.italker.factory.model.card.UserCard;

/**
 * 用户中心的基本定义
 * Created by Administrator on 2017/10/19 0019.
 */

public interface UserCenter {
    //分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(UserCard... cards);
}
