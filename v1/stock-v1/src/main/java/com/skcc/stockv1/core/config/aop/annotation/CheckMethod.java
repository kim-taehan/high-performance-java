package com.skcc.stockv1.core.config.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// method 에 annotation 을 입력시 시간체크 + IN/OUT에 대한 정보를 남긴다.
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckMethod {
}
