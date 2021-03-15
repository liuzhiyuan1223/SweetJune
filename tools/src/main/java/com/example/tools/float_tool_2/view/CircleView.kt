package com.example.tools.float_tool_2.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.tools.R
import com.example.tools.float_tool_2.LocalStatePref
import com.example.tools.float_tool_2.interfaces.IActionType
import com.example.tools.float_tool_2.interfaces.IAppCallback
import com.example.tools.float_tool_2.interfaces.ICloseCallback
import com.example.tools.float_tool_2.interfaces.ISubmitCallback

class CircleView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private var mRootView: View
    private var mContainerFun: LinearLayout
    private var mFlagFloatView: View
    private var mViewEtMsgSubmit: EditMsgSubmitView
    private var offX = 0
    private var offY = 0
    private var isPressing = false
    //判断是否是滑动操作的最小距离阈值
    private var mTouchSlop = -1
    private var mIAppCallback : IAppCallback? = null

//    private var mUrl : String? = null //地址不应该保存到这里,后续做持久化

    init {
        //测试代码
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_float_view, this)
        mFlagFloatView = mRootView.findViewById<View>(R.id.mFlagFloatView)
        mContainerFun = mRootView.findViewById(R.id.container_fun)
        mViewEtMsgSubmit = mRootView.findViewById(R.id.mViewEtMsgSubmit) //地址输入-提交框

        setTouchAction()
        initView()
    }


    private fun initView(){
        //1.点击提交后的回调
        mViewEtMsgSubmit.addSubmitCallback(object : ISubmitCallback{
            override fun onSubmit(url: String) {
                //添加具体功能
                if(!TextUtils.isEmpty(url) && mIAppCallback != null){
                    mIAppCallback?.onCallback(url) //打开端内地址的回调
                    setShowEtSubmitView(false)
                }else{
                    Toast.makeText(context, "跳转异常, 请稍后重试!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        //2.点击右上角关闭
        mViewEtMsgSubmit.addCloseCallback(object : ICloseCallback{
            override fun onClose() {
                setShowEtSubmitView(false)
            }
        })
    }

    fun addAppCallback(callback : IAppCallback?){
        mIAppCallback = callback
    }

    fun addFuns(funList : ArrayList<Int>){
        for (index in funList){
            val textView = TextView(context)
            textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            textView.setPadding(0,context.resources.getDimensionPixelSize(R.dimen._10_dp),context.resources.getDimensionPixelSize(R.dimen._10_dp),0)
            textView.text = "功能$index"
            textView.setTextColor(context.resources.getColor(R.color.white)) //白色字体
            textView.setBackgroundColor(context.resources.getColor(R.color.black)) //黑色背景

            when(index){
                IActionType.APP_CALLBACK -> {
                    textView.setOnClickListener {
//                        Toast.makeText(context, "${textView.text}被点击", Toast.LENGTH_SHORT).show()
                        setShowEtSubmitView(true)
                    }
                }

                IActionType.APP_SERVER -> {
                    textView.setOnClickListener {
//                        Toast.makeText(context, "${textView.text}被点击", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            mContainerFun.addView(textView)
        }
    }

    private fun handleClick(){
        mContainerFun.visibility = if (mContainerFun.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchAction() { //添加触摸事件监听:滑动更新位置
        //添加点击事件
//        mFlagFloatView.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                mContainerFun.visibility = if (mContainerFun.visibility == View.VISIBLE) View.GONE else View.VISIBLE
//            }
//        })

        var startX = 0
        var startY = 0
        // 是否点击
        var isPerformClick = false

        //添加触摸滑动事件
        mRootView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x.toInt()
                    startY = event.y.toInt()

                    offX = event.rawX.toInt() - view.x.toInt()
                    offY = event.rawY.toInt() - view.y.toInt()
                    isPressing = true
                    isPerformClick = true
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
//                    if (isPerformClick) view.performClick()
                    if (isPerformClick) handleClick()
                    isPressing = false
                    LocalStatePref.saveLocation(
                        context,
                        view.x.toInt(),
                        view.y.toInt()
                    )
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (Math.abs(startX - event.getX()) >= mTouchSlop || Math.abs(startY - event.getY()) >= mTouchSlop) {
                        isPerformClick = false;
                    }

                    if (isPressing) {
                        view.x = event.rawX - offX
                        view.y = event.rawY - offY
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    fun setShowEtSubmitView(isShow : Boolean){
        if(isShow){
            mViewEtMsgSubmit.visibility = View.VISIBLE
            mContainerFun.visibility = View.GONE
        }else{
            mViewEtMsgSubmit.visibility = View.GONE
            mContainerFun.visibility = View.VISIBLE
        }
    }
}