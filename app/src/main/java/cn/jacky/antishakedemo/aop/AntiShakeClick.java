package cn.jacky.antishakedemo.aop;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:Hzj
 * @date :2018/12/12/012
 * desc  ：AOP 处理按钮重复点击事件
 * record：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AntiShakeClick {
    /* 被点击的控件id*/
    @IdRes int viewId() default 0;

    /* 默认预判间隔时间1秒，手速再快也差不多了*/
    long timeInterval() default 1000;
}
