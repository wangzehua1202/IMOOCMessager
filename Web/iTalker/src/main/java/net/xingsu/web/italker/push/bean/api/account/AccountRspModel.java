package net.xingsu.web.italker.push.bean.api.account;

import com.google.gson.annotations.Expose;
import net.xingsu.web.italker.push.bean.card.UserCard;
import net.xingsu.web.italker.push.bean.db.User;

/**
 * 账户部分返回的Model
 * Created by Administrator on 2017/9/27 0027.
 */
public class AccountRspModel {
    //用户基本信息
    @Expose
    private UserCard user;
    //当前登录的账号
    @Expose
    private String account;
    //当前登录成功后获取的Token,可以通过Token获取用户的所有信息
    @Expose
    private String token;
    //标识是否已经绑定到了设备PushId上
    @Expose
    private boolean isBind;

    //默认没有绑定
    public AccountRspModel(User user) {
        this(user,false);
    }

    public AccountRspModel(User user, boolean isBind) {
        this.user = new UserCard(user);
        this.account = user.getPhone();
        this.token = user.getToken();
        this.isBind = isBind;
    }

    public UserCard getUser() {
        return user;
    }

    public void setUser(UserCard user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
