package com.xingsu.italker.factory.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.xingsu.italker.common.utils.HashUtil;
import com.xingsu.italker.factory.Factory;

import java.io.File;
import java.util.Date;

/**
 * 上传工具类，用于上传任意文件到阿里oss存储
 */

public class UploadHelper {
    private static final String TAG = UploadHelper.class.getSimpleName();
    //存储区域终节点
    private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    //上传的仓库名
    private static final String BUCKET_NAME = "italker1202";


    private static OSSClient getClient(){
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI1YLCQzhjsOyF","uDiTU5UjnmoMW6tllWSoVPjsbaWgBg");
        return new OSSClient(Factory.app(),ENDPOINT,credentialProvider);
    }


    /**
     * 上传的最终方法，成功返回一个路径
     * @param objkey 上传上去后，在服务器上一个独立的key
     * @param path 需要上传的文件的路径
     * @return 存储的地址
     */
    private static String upload(String objkey, String path){
        //构造一个上传的请求
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,objkey,path);

        try{
            //初始化上传的Client
            OSS client =getClient();
            //开始同步上传
            PutObjectResult result = client.putObject(request);
            //得到一个外网可访问的地址
            String url = client.presignConstrainedObjectURL(BUCKET_NAME,objkey,24*60*60*1000);
            //格式打印输出
            Log.d(TAG, String.format("ConstrainedObjectURL:%s",url));
            return url;
        }catch (Exception e){
            e.printStackTrace();
            //如果有异常则返回空
            return null;
        }
    }

    /**
     * 上传普通图片
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadImage(String path){
        String key = getImageObjKey(path);
        return upload(key,path);
    }

    /**
     * 上传头像
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadPortrait(String path){
        String key = getPortraitObjKey(path);
        return upload(key,path);
    }

    /**
     * 上传音频
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadAudio(String path){
        String key = getAtdioObjKey(path);
        return upload(key,path);
    }

    /**
     * 分月存储，避免一个文件夹内文件太多
     * @return yyyyMM
     */
    private static String getDateString(){
        return DateFormat.format("yyyyMM",new Date()).toString();
    }

    /**
     * /image/201709/随机字符串.jpg
     * @param path
     * @return
     */
    private static String getImageObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("image/%s/%s.jpg",dateString,fileMd5);
    }

    /**
     * /portrait/201709/随机字符串.jpg
     * @param path
     * @return
     */
    private static String getPortraitObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("portrait/%s/%s.jpg",dateString,fileMd5);
    }

    /**
     * /audio/201709/随机字符串.mp3
     * @param path
     * @return
     */
    private static String getAtdioObjKey(String path){
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("audio/%s/%s.mp3",dateString,fileMd5);
    }
}
