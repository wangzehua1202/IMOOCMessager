package com.xingsu.italker.push.helper;

import android.app.FragmentManager;
import android.content.Context;
import android.util.SparseArray;

import com.xingsu.italker.common.app.Fragment;

/**
 *
 * 完成对Fragment的调度和重用问题
 * 达到最优的Fragment切换
 * Created by 王泽华 on 2017/9/10.
 */

public class NavHelper<T> {
    //所有的Tab集合
    private final SparseArray<Tab<T>> tabs = new SparseArray();

    //用于初始化的必须参数
    private final Context context;
    private final int containerId;
    private final FragmentManager fragmentManager;
    private final OnTabChangedListener<T> listener;

    //存储当前选中的一个Tab
    private Tab<T> currentTab;

    public NavHelper(Context context,
                     int containerId,
                     FragmentManager fragmentManager,
                     OnTabChangedListener<T> listener) {
        this.context = context;
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    /**
     * 添加Tab
     * @param menuId Tab对应的菜单Id
     * @param tab Tab
     */
    public NavHelper<T> add(int menuId,Tab<T> tab){
        tabs.put(menuId, tab);
        return this;
    }

    /**
     * 执行点击菜单的操作
     * @param menuId 菜单的Id
     * @return 是否能够处理这个点击
     */
    public boolean performClickMenu(int menuId){


        return true;
    }

    /**
     * 我们所有的Tab基础属性
     * @param <T> 泛型的额外参数
     */
    public static class Tab<T>{
        //Fragment对应的Class信息
        public Class<? extends Fragment> clx;
        //额外的字段，用户自己设定需要使用
        public T extra;
    }

    /**
     * 定义事件处理完成后的回掉接口
     * @param <T>
     */
    public interface OnTabChangedListener<T>{
        void onTabChanged(Tab<T> newTab,Tab<T> oldTab);
    }
}
