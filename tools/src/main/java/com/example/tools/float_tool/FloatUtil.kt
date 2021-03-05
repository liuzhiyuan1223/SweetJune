package com.example.tools.float_tool

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.*
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.tools.R

class FloatUtil(context: Context) {

    private val mContext = context
    //创建悬浮窗
    private val mTouchSlop by lazy { ViewConfiguration.get(mContext).scaledTouchSlop }
    private val layoutParams by lazy { WindowManager.LayoutParams() }
    private val windowManager by lazy {mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager}
    private var statusBarHeight = -1
    private var isShowFun = false

    fun createFloateView(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && hasFloatPermission()){
            //用于检测状态栏高度.
            val resourceId: Int = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId)
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            layoutParams.format = PixelFormat.TRANSLUCENT // 支持透明
            layoutParams.format = PixelFormat.RGBA_8888
            layoutParams.flags = layoutParams.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 焦点
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.x = 0 //窗口位置的偏移量
            layoutParams.y = 0

            val view: View = showFloatView()
            windowManager.addView(view, layoutParams)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showFloatView(): View {
        val floatView = LayoutInflater.from(mContext).inflate(R.layout.layout_float_view, null)
        val flagFloatView = floatView.findViewById<View>(R.id.flag_float_view)
        val mTvFunOne = floatView.findViewById<TextView>(R.id.fun_one) //第一项功能
        mTvFunOne.setOnClickListener {
            Toast.makeText(mContext, "fun - 1", Toast.LENGTH_SHORT).show()
        }
        val mTvFunTwo = floatView.findViewById<TextView>(R.id.fun_two) //第二项功能
        mTvFunTwo.setOnClickListener {
            Toast.makeText(mContext, "fun - 2", Toast.LENGTH_SHORT).show()
        }
        val mContainerFun = floatView.findViewById<LinearLayout>(R.id.container_fun)
        floatView.setOnTouchListener(object : OnTouchListener {
            private var startX = 0
            private var startY = 0
            // 是否点击
            var isPerformClick = false
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.rawX.toInt()
                        startY = event.rawY.toInt()
                        isPerformClick = true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (Math.abs(startX - event.x) >= mTouchSlop || Math.abs(startY - event.y) >= mTouchSlop) {
                            isPerformClick = false
                            val nowX = event.rawX.toInt()
                            val nowY = event.rawY.toInt()
                            val movedX = nowX - startX
                            val movedY = nowY - startY
                            startX = nowX
                            startY = nowY
                            layoutParams.x = layoutParams.x + movedX
                            layoutParams.y = layoutParams.y + movedY
                            windowManager.updateViewLayout(view, layoutParams)
                        }
                    }
                    MotionEvent.ACTION_UP -> if (isPerformClick) handleFloatIconClick(mContainerFun)
                }
                return false
            }
        })
        return floatView
    }

    private fun handleFloatIconClick(view: View) {
        if (!isShowFun) { //不展示的情况下
            isShowFun = !isShowFun
            view.visibility = View.VISIBLE
        } else {
            isShowFun = !isShowFun
            view.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasFloatPermission(): Boolean {
        return Settings.canDrawOverlays(mContext)
    }

}