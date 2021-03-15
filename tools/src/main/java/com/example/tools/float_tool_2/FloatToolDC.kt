package com.example.tools.float_tool_2

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.NonNull
import com.example.tools.R
import com.example.tools.float_tool_2.interfaces.IActionType
import com.example.tools.float_tool_2.interfaces.IAppCallback
import com.example.tools.float_tool_2.view.CircleView

//基于dcv（decorView的悬浮窗工具）
private const val TAG = "FloatToolDC"
object FloatToolDC {
    //初始化方法
    private lateinit var mApp : Application
    fun getContext(): Context = mApp.applicationContext!!

    private lateinit var floatView : CircleView

    //tool初始化
    fun init(@NonNull app : Application){
        mApp = app
        //注册监听所有的activity的生命周期
        mApp.registerActivityLifecycleCallbacks(FloatViewActivityLifecycleCallbacks())
        initFloatView()
    }

    fun addAppCallback(callback : IAppCallback){
        floatView.addAppCallback(callback)
    }

    private fun initFloatView(){
        //添加自定义view
        floatView = CircleView(getContext())
        floatView.x = LocalStatePref.loadLocationX(getContext()).toFloat()
        floatView.y = LocalStatePref.loadLocationY(getContext()).toFloat()
//        floatView.addFuns(5)
        val list = ArrayList<Int>()
        list.add(IActionType.APP_CALLBACK)
        list.add(IActionType.APP_SERVER)
        floatView.addFuns(list)
    }

    //创建并attach悬浮窗
    @SuppressLint("ClickableViewAccessibility")
    fun attatchFloatView(activity: Activity){
        val decorView = activity.window.decorView as FrameLayout
        Log.d(TAG, "decorView.childCount = ${decorView.childCount} ")
        //添加到decorView
        decorView.addView(floatView)
    }
}