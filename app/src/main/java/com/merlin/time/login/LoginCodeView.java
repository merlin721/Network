package com.merlin.time.login;

import com.merlin.time.login.model.LoginCodeModel;
import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public interface LoginCodeView extends BaseMvpView {
    /**
     * 跳转到主页面。
     */
    void goMain(LoginCodeModel model);

    void getCode();

    void goError();
}
