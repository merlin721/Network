package com.merlin.time.login;

import com.soyoung.component_base.mvp.view.BaseMvpView;

/**
 * @author zhouyang
 * @date 2018/10/28
 * @desc
 */
public interface LoginView extends BaseMvpView {

    /**
     * 跳转到输入验证码界面
     * @param phone
     */
    void goCode(String phone);

}
