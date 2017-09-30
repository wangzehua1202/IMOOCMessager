package com.xingsu.italker.factory.model.card;

import com.xingsu.italker.factory.model.db.User;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class UserCard {
    private String id;
    private String name;
    private String phone;

    private String portrait;
    private String desc;
    private int sex = 0;

    private int follows;
    private int following;
    private boolean isFollow;

    private Date modifyAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    //缓存一个对应的User
    //不能被Gson框架解析，所以过滤掉
    private transient User user;

    public User build(){
        if(user == null){
            User user = new User();

            user.setId(id);;
            user.setName(name);
            user.setPhone(phone);

            user.setPortrait(portrait);
            user.setDesc(desc);
            user.setSex(sex);

            user.setFollows(follows);
            user.setFollowing(following);
            user.setFollow(isFollow);

            user.setModifyAt(modifyAt);
            this.user = user;
        }
        return user;

    }
}