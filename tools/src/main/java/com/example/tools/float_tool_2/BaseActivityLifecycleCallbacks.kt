package com.example.tools.float_tool_2

import android.app.Activity
import android.app.Application
import android.os.Bundle

//可继承的
open class BaseActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
    }

    override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPreSaveInstanceState(activity, outState)
    }

    override fun onActivityStarted(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivityPostStopped(activity: Activity) {
        super.onActivityPostStopped(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPostCreated(activity, savedInstanceState)
    }

    override fun onActivityPostStarted(activity: Activity) {
        super.onActivityPostStarted(activity)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//        TODO("Not yet implemented")
    }

    override fun onActivityResumed(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivityPreDestroyed(activity: Activity) {
        super.onActivityPreDestroyed(activity)
    }

    override fun onActivityPreResumed(activity: Activity) {
        super.onActivityPreResumed(activity)
    }

    override fun onActivityPostResumed(activity: Activity) {
        super.onActivityPostResumed(activity)
    }

    override fun onActivityPostPaused(activity: Activity) {
        super.onActivityPostPaused(activity)
    }

    override fun onActivityPreStopped(activity: Activity) {
        super.onActivityPreStopped(activity)
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        super.onActivityPostDestroyed(activity)
    }

    override fun onActivityPrePaused(activity: Activity) {
        super.onActivityPrePaused(activity)
    }

    override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPostSaveInstanceState(activity, outState)
    }

    override fun onActivityPreStarted(activity: Activity) {
        super.onActivityPreStarted(activity)
    }

}