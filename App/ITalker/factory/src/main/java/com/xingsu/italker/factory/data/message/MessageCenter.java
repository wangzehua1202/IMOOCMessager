package com.xingsu.italker.factory.data.message;

import com.xingsu.italker.factory.model.card.MessageCard;

/**
 * 消息中心，进行消息卡片的消费
 * Created by Administrator on 2017/10/19 0019.
 */

public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
