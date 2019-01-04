package cn.jacky.antishakedemo.aop;

import android.os.SystemClock;
import android.util.Log;

import cn.jacky.antishakedemo.BuildConfig;


/**
 * @author:Hzj
 * @date :2018/12/12/012
 * desc  ：判断是否是短时间内重复点击工具类
 * record：
 */
public class AntiShakeClickUtil {
    private static final String TAG = "AntiShakeClickUtil";
    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;

    /**
     * 是否是快速点击
     *
     * @param clickedId      点击的控件id
     * @param intervalMillis 时间间期（毫秒）
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(int clickedId, long intervalMillis) {
        long time = SystemClock.elapsedRealtime();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (clickedId == mLastClickViewId && timeInterval < intervalMillis) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "isFastDoubleClick:true ");
            }
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = clickedId;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "isFastDoubleClick: false");
            }
            return false;
        }
    }
}
