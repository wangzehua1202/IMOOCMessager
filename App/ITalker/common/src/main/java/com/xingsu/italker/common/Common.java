package com.xingsu.italker.common;

/**
 * Created by 王泽华 on 2017/9/2.
 */

public class Common {
    /**
     * 一些不可变的永恒的参数
     * 通常用于一些配置
     */
    public interface Constance{
        //手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";
    }
}
