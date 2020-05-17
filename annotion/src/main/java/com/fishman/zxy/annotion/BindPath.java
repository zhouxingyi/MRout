package com.fishman.zxy.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)           //作用域
@Retention(RetentionPolicy.CLASS)  //生命周期
public @interface BindPath {
    String value();
}
