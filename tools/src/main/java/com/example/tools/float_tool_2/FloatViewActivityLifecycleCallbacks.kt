package com.example.tools.float_tool_2

import android.app.Activity
import android.os.Bundle
import android.util.Log

private const val TAG = "FloatViewActivityLifecy"
class FloatViewActivityLifecycleCallbacks : BaseActivityLifecycleCallbacks() {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityCreated(activity, savedInstanceState)
        Log.d(TAG, "onActivityCreated: ")

        //向当前activity添加悬浮窗
        FloatToolDC.attatchFloatView(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        super.onActivityResumed(activity)
        Log.d(TAG, "onActivityResumed: ")
    }
}