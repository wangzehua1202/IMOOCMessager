package com.xingsu.italker.factory.data.user;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.data.helper.DbHelper;
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

public class ContactRepository implements ContactDataSource,
        QueryTransaction.QueryResultListCallback<User>,
        DbHelper.ChangedListener<User>{
    private final Set<User> users = new HashSet<>();

    private DataSource.SucceedCallback<List<User>> callback;

    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        this.callback = callback;
        //对数据辅助工具类添加一个数据更新的监听
        DbHelper.addChangedListener(User.class, this);
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

    @Override
    public void dispose() {
        this.callback = null;
        //取消对数据集合的监听
        DbHelper.removeChangedListener(User.class, this);
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<User> tResult) {
        //数据库加载数据成功
        if(tResult.size() == 0){
           users.clear();
            notifyDataChange();
            return;
        }
        //转变为数组
        User[] users = tResult.toArray(new User[0]);
        //回到数据集更新的操作中
        onDataSave(users);
    }



    @Override
    public void onDataSave(User... list) {
        boolean isChanged = false;
        //当数据库变更的操作
        for (User user : list) {
            //是关注的人，同时不是我自己
            if(isRequired(user)){
                insertOrUpdate(user);
                isChanged = true;
            }
        }
        //有数据变更则进行界面刷新
        if(isChanged)
            notifyDataChange();
    }

    @Override
    public void onDataDelete(User... list) {
        boolean isChanged = false;
        //当数据库删除的操作
        for (User user : list) {
            if(users.remove(user))
                isChanged = true;

        }
        //有数据变更则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    List<User> users = new LinkedList<>();

    private void insertOrUpdate(User user){
        int index = indexOf(user);
        if(index >= 0){
            replace(index,user);
        }else{
            insert(user);
        }
    }

    private void replace(int index, User user){
        users.remove(index);
        users.add(index,user);
    }

    //添加方法
    private void insert(User user){
        users.add(user);
    }

    private int indexOf(User user){
        int index = -1;
        for (User user1 : users) {
            index++;
            if(user1.isSame(user)){
                return index;
            }
        }
        return -1;
    }

    private void notifyDataChange(){
        if(callback != null)
            callback.onDataLoaded(users);
    }

    /**
     * 检查一个user是否是我需要关注的数据
     * @param user User
     * @return true 是我关注的数据
     */
    private boolean isRequired(User user){
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }
}
