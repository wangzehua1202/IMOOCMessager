package com.xingsu.italker.factory.data.user;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.data.BaseDbRespository;
import com.xingsu.italker.factory.data.helper.DbHelper;
import com.xingsu.italker.factory.model.db.BaseDbModel;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.persistence.Account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 联系人仓库
 * Created by Administrator on 2017/10/19 0019.
 */

public class ContactRepository extends BaseDbRespository<User> implements ContactDataSource{
    private DataSource.SucceedCallback<List<User>> callback;

    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);
        //对数据辅助工具类添加一个数据更新的监听
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name,true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    /**
     * 检查一个user是否是我需要关注的数据
     * @param user User
     * @return true 是我关注的数据
     */
    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }



}
