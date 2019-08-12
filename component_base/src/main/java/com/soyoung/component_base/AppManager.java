package com.soyoung.component_base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.soyoung.component_base.util.LogUtils;

import java.util.Iterator;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }



    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取上个activity
     */
    public Activity getForwardActivity() {
        if (activityStack == null) {
            return null;
        } else if (activityStack.size() <= 1) {
            return null;
        } else {
            return activityStack.get(activityStack.size() - 2);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
        }
    }
    public void deleteActivity(Activity activity){
        if (null!=activity&&null!=activityStack) {
            if (activityStack.size()>0) {
                boolean contains = activityStack.contains(activity);
                if (contains) {
                    activityStack.remove(activity);
                }
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        //用iterator迭代器进行遍历
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (null != activityStack&&activityStack.size()>0) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        finishAllActivity();
        ActivityManager activityMgr =
            (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.killBackgroundProcesses(context.getPackageName());
    }

    public boolean hasCls(Class<?> cls) {
        if (null != activityStack) {
            for (int i = activityStack.size() - 1; i >= 0; i--) {
                if (null != activityStack.get(i) && cls.equals(activityStack.get(i).getClass())) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 切換到指定的activity
     */
    public void getSpecifyActivity(Class<?> cls) {
        if (null != activityStack) {
            for (int i = activityStack.size() - 1; i >= 0; i--) {
                if (null != activityStack.get(i) && !cls.equals(activityStack.get(i).getClass())) {
                    activityStack.get(i).finish();
                } else {
                    break;
                }
            }
        }
    }
}