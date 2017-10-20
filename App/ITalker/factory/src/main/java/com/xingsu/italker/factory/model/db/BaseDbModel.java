package com.xingsu.italker.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xingsu.italker.factory.utils.DiffUiDataCallback;

/**
 * 集成数据库框架DbFlow的基础类
 * 同时定义需要的方法
 * Created by Administrator on 2017/10/20 0020.
 */

public abstract class BaseDbModel<Model> extends BaseModel implements DiffUiDataCallback.UiDataDiffer<Model>{

}
