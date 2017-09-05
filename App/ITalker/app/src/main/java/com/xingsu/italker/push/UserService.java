package com.xingsu.italker.push;

/**
 * Created by 王泽华 on 2017/9/5.
 */

public class UserService implements IUserService{
   @Override
    public String search(int hashCode){
        return "User:" + hashCode;
    }
}
