package net.xingsu.web.italker.push.factory;

import net.xingsu.web.italker.push.bean.db.Group;
import net.xingsu.web.italker.push.bean.db.GroupMember;
import net.xingsu.web.italker.push.bean.db.User;

import java.util.Set;

/**
 * 群数据处理类
 * Created by Administrator on 2017/10/20 0020.
 */
public class GroupFactory {
    public static Group findById(String groupId) {
        //TODO 查询一个群
        return null;
    }

    public static Group findById(User sender, String receiverId) {
        //TODO 查询一个群，同时该User必须为群的成员，否则返回一个null
        return null;
    }

    public static Set<GroupMember> getMembers(Group group) {
        //TODO 查询一个群的群成员
        return null;

    }

}
