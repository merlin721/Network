package com.soyoung.component_base.mvp.factory;


import com.soyoung.component_base.mvp.presenter.BaseMvpPresenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2018/2/27
 * description   : 自定义注解
 */
@Target(ElementType.TYPE)//用于描述类、接口(包括注解类型) 或enum声明
@Inherited//如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个Annotation将被用于该class的子类。
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface CreatePresenter {
    Class<? extends BaseMvpPresenter> value();
}
