package com.xingsu.italker.factory.data.helper;

import com.xingsu.italker.common.factory.data.DataSource;
import com.xingsu.italker.factory.R;
import com.xingsu.italker.factory.model.api.account.RegisterModel;
import com.xingsu.italker.factory.model.db.User;

/**
 * Created by 王泽华 on 2017/9/28.
 */

public class AccountHelper {

    /**
     * 注册的接口,异步的调用
     * @param model 传递一个注册的Model进来
     * @param callback 成功与失败的接口回送
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback){
        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                callback.onDataNotAvailable(R.string.data_rsp_error_parameters);
            }
        }.start();
    }
}
