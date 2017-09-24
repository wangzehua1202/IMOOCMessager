package com.xingsu.italker.common.app;

import android.os.SystemClock;

import java.io.File;

/**
 * Created by 王泽华 on 2017/9/24.
 */

public class Application extends android.app.Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 获取缓存文件夹地址
     * @return 当前App的缓存文件夹地址
     */
    public static File getCacheDirFile(){
        return instance.getCacheDir();
    }

    public static File getPortraitTmpFile(){
        //得到头像目录的缓存地址
        File dir = new File(getCacheDirFile(),"portrait");
        //创建所有的对应的文件夹
        dir.mkdirs();

        //删除旧的一些缓存文件
        File[] files = dir.listFiles();
        if(files != null && files.length > 0 ){
            for(File file : files){
                file.delete();
            }
        }

        //返回一个当前时间戳的目录文件地址
        File path = new File(dir, SystemClock.uptimeMillis()+".jpg");
        return path.getAbsoluteFile();
    }
}
