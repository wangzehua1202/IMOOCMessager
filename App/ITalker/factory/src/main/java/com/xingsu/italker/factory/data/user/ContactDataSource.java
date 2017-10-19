package com.xingsu.italker.factory.data.user;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.model.db.User;

import java.util.List;

/**
 * 联系人数据源
 * Created by Administrator on 2017/10/19 0019.
 */

public interface ContactDataSource {
    /**
     * 对数据进行加载的一个职责
     * @param callback 加载成功后返回的Callback
     */
    void load(DataSource.SucceedCallback<List<User>> callback);

    /**
     * 销毁操作
     */
    void dispose();
}
