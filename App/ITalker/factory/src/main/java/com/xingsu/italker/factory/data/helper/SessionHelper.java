package com.xingsu.italker.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xingsu.italker.factory.model.db.Session;
import com.xingsu.italker.factory.model.db.User;

/**
 * 会话的辅助工具类
 * Created by Administrator on 2017/10/19 0019.
 */

public class SessionHelper {
    //从本地查询session
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
