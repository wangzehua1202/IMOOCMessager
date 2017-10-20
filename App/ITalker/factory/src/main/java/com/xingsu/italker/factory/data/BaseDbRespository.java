package com.xingsu.italker.factory.data;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.common.factory.data.DbDataSource;
import com.xingsu.italker.common.factory.presenter.BaseContract;
import com.xingsu.italker.common.utils.CollectionUtil;
import com.xingsu.italker.factory.data.helper.DbHelper;
import com.xingsu.italker.factory.model.db.BaseDbModel;
import com.xingsu.italker.factory.model.db.User;
import com.xingsu.italker.factory.persistence.Account;

import net.qiujuer.genius.kit.reflect.Reflector;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 基础的数据库仓库
 * 实现对数据库的基本的监听操作
 * Created by Administrator on 2017/10/20 0020.
 */

public abstract class BaseDbRespository<Data extends BaseDbModel<Data>> implements DbDataSource<Data>,
        DbHelper.ChangedListener<Data>, QueryTransaction.QueryResultListCallback<Data>{
    //和Presenter交互的回调
    private SucceedCallback<List<Data>> callback;
    final private List<Data> dataList = new LinkedList<>();    //当前缓存的数据
    private Class<Data> dataClass;      //当前泛型对应真实class信息

    public BaseDbRespository() {
        //拿到当前类的泛型数组信息
        Type[] types = Reflector.getActualTypeArguments(BaseDbRespository.class, this.getClass());

        dataClass = (Class<Data>) types[0];
    }

    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.callback = callback;
        //进行数据库监听操作
        regosterDbChangedListener();
    }

    @Override
    public void dispose() {
        //取消监听，销毁数据
        this.callback = null;
        DbHelper.removeChangedListener(dataClass, this);
        dataList.clear();
    }

    //数据库统一通知的地方：增加/更改
    @Override
    public void onDataSave(Data... list) {
        boolean isChanged = false;
        //当数据库变更的操作
        for (Data data : list) {
            //是关注的人，同时不是我自己
            if(isRequired(data)){
                insertOrUpdate(data);
                isChanged = true;
            }
        }
        //有数据变更则进行界面刷新
        if(isChanged)
            notifyDataChange();
    }

    //数据库统一通知的地方：删除
    @Override
    public void onDataDelete(Data... list) {

        boolean isChanged = false;
        //当数据库删除的操作
        for (Data data : list) {
            if(dataList.remove(data))
                isChanged = true;

        }
        //有数据变更则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    //DbFlow 框架通知的回调
    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Data> tResult) {
        //数据库加载数据成功
        if(tResult.size() == 0){
            dataList.clear();
            notifyDataChange();
            return;
        }
        //转变为数组
        Data[] users = CollectionUtil.toArray(tResult, dataClass);
        //回到数据集更新的操作中
        onDataSave(users);
    }

    //插入或者更新
    protected void insertOrUpdate(Data data){
        int index = indexOf(data);
        if(index >= 0){
            replace(index,data);
        }else{
            insert(data);
        }
    }

    //更新某个坐标下的数据
    protected void replace(int index, Data data){
        dataList.remove(index);
        dataList.add(index,data);
    }

    //添加方法
    protected void insert(Data data){
        dataList.add(data);
    }

    //查询一个数据是否在当前的缓存数据中，如果在返回坐标
    protected int indexOf(Data newData){
        int index = -1;
        for (Data data : dataList) {
            index++;
            if(data.isSame(newData)){
                return index;
            }
        }
        return -1;
    }

    /**
     * 检查一个data是否是我需要关注的数据
     * @param data Data
     * @return true 是我关注的数据
     */
    protected abstract boolean isRequired(Data data);

    /**
     * 添加数据库的监听操作
     */
    protected void regosterDbChangedListener(){
        DbHelper.addChangedListener(dataClass,this);
    }

    //通知界面刷新的方法
    private void notifyDataChange(){
        SucceedCallback<List<Data>> callback = this.callback;
        if(callback != null)
            callback.onDataLoaded(dataList);
    }
}
