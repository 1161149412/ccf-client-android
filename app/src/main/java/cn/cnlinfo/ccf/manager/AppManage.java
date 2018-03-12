package cn.cnlinfo.ccf.manager;

import android.content.Context;

import java.util.Stack;

import cn.cnlinfo.ccf.inter.IActivityFinish;


public class AppManage {

    private static final AppManage instance = new AppManage();

    public static AppManage getInstance() {
        synchronized (instance) {
            return instance;
        }
    }

    private Stack<IActivityFinish> activities;

    private AppManage() {
        activities = new Stack<IActivityFinish>();
    }

    public void addActivity(IActivityFinish activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }


    public void removeActivity(IActivityFinish activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    public void finishOther() {
        IActivityFinish activity = getCurrentActivity();
        while (!activities.isEmpty()) {
            IActivityFinish act = activities.pop();
            if (act != activity && act != null) {
                act.finishActivity();
            }
        }
        addActivity(activity);
    }

    public void finishActivity(IActivityFinish activity) {
        if (activity != null) {
            activities.remove(activity);
        }
        activity.finishActivity();
    }

    public IActivityFinish getCurrentActivity() {
        if (activities.size() > 0) {
            return activities.lastElement();
        } else {
            return null;
        }
    }


    @SuppressWarnings("deprecation")
    public void exit(Context context) {
        clearEverything();
        activities.removeAllElements();
        activities.clear();
        activities = null;
        System.exit(0);
    }

    public void clearEverything() {
        while (!activities.isEmpty()) {
            IActivityFinish act = activities.pop();
            try {
                if (act != null) {
                    act.finishActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
