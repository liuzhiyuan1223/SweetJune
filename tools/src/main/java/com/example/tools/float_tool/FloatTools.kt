package com.example.tools.float_tool

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.*
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentActivity
import com.example.tools.R
import com.example.tools.float_tool.fun_1.IStartWebListener
import com.example.tools.model.FunClickModel

class FloatTools private constructor() {

    companion object {
        @JvmStatic
        fun get(): FloatTools = mInstance!!

        private var mInstance: FloatTools? = null
            get() {
                if (field == null) field = FloatTools()
                return field
            }
    }

    private lateinit var mApp : Application
    fun getContext(): Context = mApp.applicationContext!!
    fun init(@NonNull app : Application){
        mApp = app
    }

    private val layoutParams by lazy { WindowManager.LayoutParams() }
    private val windowManager by lazy { getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private var statusBarHeight = -1
    private var isShowFun = false

    private var mFloatView : View? = null

    fun createFloateView(activity: FragmentActivity, listener: IStartWebListener) {
        if(mFloatView == null) {
            val funClickModels = FunctionConfig().buildFunConfig(activity, listener)
            mFloatView = createFloateView(getContext(), funClickModels)
            initViewParams()
            windowManager.addView(mFloatView, layoutParams)
        }else{
            Toast.makeText(mApp, "悬浮窗已经创建!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun createFloateView(mContext: Context, funClickModels: ArrayList<FunClickModel>) : View?{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && hasFloatPermission()) {
            //用于检测状态栏高度.
            val resourceId: Int = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId)
            return showFloatView(mContext, funClickModels)
        }else{
            //申请权限
            return null
        }
    }

    private fun initViewParams(){
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        layoutParams.format = PixelFormat.TRANSLUCENT // 支持透明
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.flags =
            layoutParams.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 焦点
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.x = 0 //窗口位置的偏移量
        layoutParams.y = 0
    }


    //依据ClickListener数量创建相应数量的功能btn
    private fun buildBtnUponData(
        container: LinearLayout,
        funClickModels: ArrayList<FunClickModel>
    ) {
        for (funModel in funClickModels) {
            val funTv = TextView(getContext())
            funTv.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            funTv.setPadding(100) //功能键的padding
            funTv.text = funModel.funName
            funTv.setOnClickListener(funModel.clickListener)
            container.addView(funTv)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun showFloatView(mContext: Context, funClickModels: ArrayList<FunClickModel>): View {
        val mTouchSlop by lazy { ViewConfiguration.get(mContext).scaledTouchSlop }
        val floatView = LayoutInflater.from(mContext).inflate(R.layout.layout_float_view, null)
        val flagFloatView = floatView.findViewById<View>(R.id.flag_float_view)

        //第一个功能键盘为项目介绍预留，计划使用一个端内H5来展示
        val mTvFunOne =
            floatView.findViewById<TextView>(R.id.fun_one) //第一项功能 Toast.makeText(mContext, "fun - 1", Toast.LENGTH_SHORT).show()
        mTvFunOne.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })

        //承载功能按钮的容器
        val mContainerFun = floatView.findViewById<LinearLayout>(R.id.container_fun)
        buildBtnUponData(mContainerFun, funClickModels)

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
        return Settings.canDrawOverlays(getContext())
    }

}