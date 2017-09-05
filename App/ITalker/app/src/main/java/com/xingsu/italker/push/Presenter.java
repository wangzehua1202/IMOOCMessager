package com.xingsu.italker.push;

import android.text.TextUtils;

/**
 * Created by 王泽华 on 2017/9/5.
 */

public class Presenter implements IPresenter{
    private IView mView;
    public Presenter(IView view){
        mView = view;
    }

    @Override
    public void search() {
        //开启界面Loading
        String inputString = mView.getInputString();
        if(TextUtils.isEmpty(inputString))
            return ;  //为空直接返回

        int hashCode = inputString.hashCode();
        IUserService service = new UserService();

        String serviceResult = service.search(hashCode);
        String result = "Result:" + inputString + "-" + serviceResult;

        //关闭界面Loading
        mView.setResultString(result);
    }
}
