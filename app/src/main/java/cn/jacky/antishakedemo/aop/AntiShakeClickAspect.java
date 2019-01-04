package cn.jacky.antishakedemo.aop;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import cn.jacky.antishakedemo.BuildConfig;

/**
 * @author:Hzj
 * @date :2018/12/12/012
 * desc  ：AOP 处理按钮短时间内重复点击
 * record：
 */
@Aspect
public class AntiShakeClickAspect {
    private static final long DEFAULT_TIME_INTERVAL = 1000;

    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 注意：！！！！！这的路径要替换成你自己的路径，否则不会生效！！！
     * 注意:两个* 之间要有空格
     */
    @Pointcut("execution(@cn.jacky.antishakedemo.aop.AntiShakeClick * *(..))")
    public void methodAnnotated() {
    }

    /**
     * 定义一个切面方法，环绕切点方法
     * 优先取view.getId() 作为id
     */
    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出SingleClick(View view)方法参数
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        //取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(AntiShakeClick.class)) {
            return;
        }
        AntiShakeClick singleClick = method.getAnnotation(AntiShakeClick.class);
        //默认取注解中的viewId,若方法参数中存在View view参数，则取view.getId()
        int targetId = singleClick.viewId();
        if (view != null) {
            targetId = view.getId();
        }
        if (BuildConfig.DEBUG) {
            Log.d("AntiShakeClickAspect","AntiShakeClickAspect;" + targetId + "--" + singleClick.timeInterval());
        }
        //判断是否是快速连击
        if (!AntiShakeClickUtil.isFastDoubleClick(targetId, singleClick.timeInterval())) {
            //不是快速连击，执行原方法
            joinPoint.proceed();
        }
    }
}
