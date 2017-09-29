package com.xingsu.italker.factory.net;

import com.xingsu.italker.common.Common;
import com.xingsu.italker.factory.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class Network {

    //构建一个Retrofit
    public static Retrofit getRetrofit(){
        //得到一个OKclient
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder();

        //设置电脑连接
        return builder.baseUrl(Common.Constance.API_URL)
                //设置client
                .client(client)
                //设置json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();

    }
}
