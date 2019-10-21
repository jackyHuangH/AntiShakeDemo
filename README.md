# AntiShakeDemo
安卓按钮点击防抖，基于AOP切面方式实现拦截
类似参考方案：https://www.cnblogs.com/yxx123/archive/2017/04/06/6675567.html?tdsourcetag=s_pctim_aiomsg

# 使用步骤：
## 1.添加aop依赖
  ### 先在project的build.gradle中添加aop插件：
  
  ```
  dependencies {
      classpath 'com.android.tools.build:gradle:3.2.1'
      //添加插件 android aspectJ AOP
      classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
  }
  ```
    
 ### 然后在App的build.gradle中添加aspectjx
 
  ```
  apply plugin: 'com.android.application'
  // 注意：主App中请确保添加aspectjx
  apply plugin: 'android-aspectjx'
  ```
  App的build.gradle中添加依赖：
  ```
  //AOP,配合aspectj 插件必须依赖
    implementation 'org.aspectj:aspectjrt:1.8.9'
  ```
  ### 复制demo中aop文件夹里的三个类到项目中
  AntiShakeClick,
  AntiShakeClickAspect,
  AntiShakeClickUtil
  
  ## 2.代码中使用，在点击事件方法中添加注解@AntiShakeClick即可
  
  ### <1>普通点击事件绑定防抖：
    ```
     //1普通点击事件绑定，注解不需要赋值id，只要点击事件里有View 作为参数即可
        mBt1.setOnClickListener(new View.OnClickListener() {
            @AntiShakeClick
            @Override
            public void onClick(View v) {
                //do something
            }
        });
    ```
   ### <2>黄油刀点击事件中绑定防抖：配合butterknife点击事件绑定，注解一定要赋值按钮的id,因为黄油刀点击方法中默认没有View参数，拿不到id，你也可以自己加上View参数，这样也不用写viewId,就像这样：
     @AntiShakeClick()
     @OnClick(R.id.bt2) 
     public void onBt2Clicked(View view) {
          //do something
     }
   ```
    @AntiShakeClick(viewId = R.id.bt2)
    @OnClick(R.id.bt2)
    public void onBt2Clicked() {
        //do something
    }
   ```
   
   # $\color{red}{注意事项：}$
   ## 1.重要的事说三遍！！！！请一定要将AntiShakeClickAspect切点方法中的注解路径替换成自己的路径，否则无法生效！！！！
  ## 2.混淆：-keep class 包名.aop.**{*;}
  ## 3.itemclickListener中尽量不要用，容易出问题。。。
   ```
    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 注意：！！！！！这的路径@cn.jacky.antishakedemo.aop.AntiShakeClick * *(..))要替换成你自己的文件路径，否则不会生效！！！
     * 注意:两个* 之间要有空格
     */
    @Pointcut("execution(@cn.jacky.antishakedemo.aop.AntiShakeClick * *(..))")
    public void methodAnnotated() {
    }
   ```
   
   # Kotlin 按钮防抖,扩展函数实现：
   ```
   /**
 * 按钮点击防抖
 */
fun View?.setOnAntiShakeClickListener(intervalMillis: Long = 1000, listener: (View) -> Unit) {
    /**
     * 最近一次点击的时间
     */
    var lastClickTime: Long = 0
    this?.setOnClickListener {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime >= intervalMillis) {
            listener.invoke(it)
            lastClickTime = currentTime
        }
    }
}
   ```
   
   
