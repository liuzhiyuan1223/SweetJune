package com.example.tools.float_tool_2.view

import android.content.Context
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.tools.R
import com.example.tools.float_tool_2.interfaces.ICloseCallback
import com.example.tools.float_tool_2.interfaces.ISubmitCallback

class EditMsgSubmitView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private var mRootView: View
    private var mEtMsg: EditText
    private var mTvAddressUsed: TextView
    private var mTvSubmitMsg: TextView
    private var mIvClose: ImageView
    //提交地址后的回调
    private var mSubmitCallback: ISubmitCallback? = null
    //右上角关闭
    private var mCloseCallback: ICloseCallback? = null

    init {
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_edit_msg_submit, this)
        mEtMsg = mRootView.findViewById<EditText>(R.id.mEtMsg)
        mTvAddressUsed = mRootView.findViewById<TextView>(R.id.mTvAddressUsed)
        mTvSubmitMsg = mRootView.findViewById<TextView>(R.id.mTvSubmitMsg)
        mIvClose = mRootView.findViewById<ImageView>(R.id.mIvClose)
        initView()
    }

    private fun initView() {
        mTvSubmitMsg.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //mEtMsg.text.toString()是用户输入的地址
                val url = mEtMsg.text.toString()
                Log.d("june", "跳转地址url = $url ")
                mSubmitCallback?.onSubmit(url) //点击提交键后的逻辑
            }
        })
        //关闭输入框
        mIvClose.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                mCloseCallback?.onClose()
            }
        })
    }

    fun addSubmitCallback(callback : ISubmitCallback?){
        mSubmitCallback = callback
    }

    fun addCloseCallback(callback : ICloseCallback?){
        mCloseCallback = callback
    }
}