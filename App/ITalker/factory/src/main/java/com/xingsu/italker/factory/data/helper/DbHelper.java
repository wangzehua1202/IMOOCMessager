package com.xingsu.italker.factory.data.helper;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.xingsu.italker.common.utils.CollectionUtil;
import com.xingsu.italker.factory.model.card.UserCard;
import com.xingsu.italker.factory.model.db.AppDatabase;
import com.xingsu.italker.factory.model.db.Group;
import com.xingsu.italker.factory.model.db.User;

import java.util.Arrays;
import java.util.List;

/**
 * 数据库的辅助工具类
 * 辅助完成增删改
 * 单例
 * Created by Administrator on 2017/10/19 0019.
 */

public class DbHelper {
    private  static final DbHelper instance;

    static {
        instance = new DbHelper();
    }

    public DbHelper() {
    }

    /**
     * 新增或者修改的统一方法
     * @param tClass 传递一个class信息
     * @param models 这个class对应的实例数组
     * @param <Model> 实例的泛型 限定条件是BaseModel
     */
    public static<Model extends BaseModel> void save(final Class<Model> tClass, final Model... models){
        if(models == null || models.length == 0)
            return;

        //当前数据库的一个管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事务
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                //执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                //保存
                adapter.saveAll(Arrays.asList(models));
                //唤起通知
                instance.notifySave(tClass,models);
            }
        }).build().execute();
    }

    /**
     * 进行删除数据库的统一封装方法
     * @param tClass
     * @param models
     * @param <Model>
     */
    public static<Model extends BaseModel> void delete(final Class<Model> tClass, final Model... models){
        if(models == null || models.length == 0)
            return;

        //当前数据库的一个管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事务
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                //执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                //删除
                adapter.deleteAll(Arrays.asList(models));
                //唤起通知
                instance.notifyDelete(tClass,models);
            }
        }).build().execute();
    }

    /**
     * 进行保存通知调用
     * @param tClass 通知的类型
     * @param models 通知的Model数组
     * @param <Model> 这个实例的泛型 限定条件是BaseModel
     */
    private final <Model extends BaseModel> void notifySave(final Class<Model> tClass, final Model... models){
        //TODO
    }

    /**
     * 进行删除通知调用
     * @param tClass 通知的类型
     * @param models 通知的Model数组
     * @param <Model> 这个实例的泛型 限定条件是BaseModel
     */
    private final <Model extends BaseModel> void notifyDelete(final Class<Model> tClass, final Model... models){
        //TODO
    }
}
