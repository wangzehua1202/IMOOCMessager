package com.xingsu.italker.push;

import android.widget.TextView;

import com.xingsu.italker.common.app.Activity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements IView{
    @BindView(R.id.txt_result)
    TextView mResultText;

    @BindView(R.id.edit_query)
    TextView mInputText;

    private IPresenter mPresemter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresemter = new Presenter(this);
    }

    @OnClick(R.id.btn_submit)
    void onSubmit(){
        mPresemter.search();
    }

    @Override
    public String getInputString() {
        return mInputText.getText().toString();
    }

    @Override
    public void setResultString(String str) {
        mResultText.setText(str);
    }
}
