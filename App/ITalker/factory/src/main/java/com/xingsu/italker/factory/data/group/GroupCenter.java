package com.xingsu.italker.factory.data.group;

import com.xingsu.italker.factory.model.card.GroupCard;
import com.xingsu.italker.factory.model.card.GroupMemberCard;
import com.xingsu.italker.factory.model.card.MessageCard;

/**
 * 群中心的接口定义
 * Created by Administrator on 2017/10/19 0019.
 */

public interface GroupCenter {
    //群卡片的处理
    void dispatch(GroupCard... cards);

    //群成员的处理
    void dispatch(GroupMemberCard... cards);
}
