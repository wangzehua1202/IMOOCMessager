package com.xingsu.italker.factory.net;

import android.text.TextUtils;

import com.xingsu.italker.common.Common;
import com.xingsu.italker.factory.Factory;
import com.xingsu.italker.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class Network {
    private static Network instance;
    private Retrofit retrofit;

    static {
        instance = new Network();
    }

    private Network(){

    }

    //构建一个Retrofit
    public static Retrofit getRetrofit(){
        if(instance.retrofit != null)
            return instance.retrofit;
        //得到一个OKclient
        OkHttpClient client = new OkHttpClient.Builder()
                //给所有的请求添加一个拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到我们的请求
                        Request original = chain.request();
                        //重新进行builder
                        Request.Builder builder = original.newBuilder();
                        if(!TextUtils.isEmpty(Account.getToken())){
                            builder.addHeader("token", Account.getToken());
                        }
                        builder.addHeader("Content-Type", "application/json");
                        Request newRequest = builder.build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();

        //设置电脑连接
        instance.retrofit =  builder.baseUrl(Common.Constance.API_URL)
                //设置client
                .client(client)
                //设置json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return instance.retrofit;
    }

    /**
     * 返回一个请求代理
     * @return RemotService
     */
    public static RemotService remote(){
        //调用Retrofit对我们网络请求接口做代理
        return Network.getRetrofit().create(RemotService.class);
    }
}
