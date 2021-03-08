package com.example.tools.float_tool.fun_1

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tools.R
import kotlinx.android.synthetic.main.fun_1_start_web.*

private const val TAG = "WebFragment"

class StartWebFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mRootView = inflater.inflate(R.layout.fun_1_start_web, container)
        return mRootView
    }

    var mClickListener : IStartWebListener? = null

    fun setIStartWebListener(listener : IStartWebListener){
        mClickListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTvStartWeb.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val urlText = mEtInputAddress.text.toString()
                if (!TextUtils.isEmpty(urlText) && mClickListener != null){
                    mClickListener?.onStartWebClick(urlText)
                }else{
                    Log.d(TAG, "Fail to start web page!")
                }
            }
        })
    }
}