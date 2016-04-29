package com.ec2.yspay.common;

import android.app.Activity;
import android.util.DisplayMetrics;
/**
 * 屏幕工具
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年9月18日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ScreenParams {
    private final String TAG = "ScreenParams";
    private static ScreenParams params;
    public int screenWidth;// 屏幕宽度，单位为px
    public int screenHeight;// 屏幕高度，单位为px
    public int densityDpi;// 屏幕密度，单位为dpi
    public float scale;// 缩放系数，值为 densityDpi/160
    public float fontScale;// 文字缩放系数，同scale

    public final static int SCREEN_ORIENTATION_VERTICAL = 1; // 屏幕状态：横屏
    public final static int SCREEN_ORIENTATION_HORIZONTAL = 2; // 屏幕状态：竖屏
    public int screenOrientation = SCREEN_ORIENTATION_VERTICAL;// 当前屏幕状态，默认为竖屏

    /**
     * 私有构造方法
     * 
     * @param activity
     */
    private ScreenParams(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        densityDpi = dm.densityDpi;
        scale = dm.density;
        fontScale = dm.scaledDensity;

        screenOrientation = screenHeight > screenWidth ? SCREEN_ORIENTATION_VERTICAL
                : SCREEN_ORIENTATION_HORIZONTAL;
    }

    /**
     * 获取实例
     * 
     * @param activity
     * @return
     */
    public static ScreenParams getInstance(Activity activity) {
        if (params == null) {
            params = new ScreenParams(activity);
        }
        return params;
    }

    /**
     * 获取一个新实例
     * 
     * @param activity
     * @return
     */
    public static ScreenParams getNewInstance(Activity activity) {
        if (params != null) {
            params = null;
        }
        return getInstance(activity);
    }

    /**
     * 参数信息
     */
    public String toString() {

        return TAG
                + ":\n screenWidth: "+ screenWidth
                + "\n screenHeight: "+ screenHeight
                + "\n scale: "+ scale
                + "\n fontScale: "+ fontScale
                + "\n densityDpi: "+ densityDpi
                + "\n screenOrientation: "+ (screenOrientation == SCREEN_ORIENTATION_VERTICAL ? "vertical"
                        : "horizontal") + "]";
    }
}
