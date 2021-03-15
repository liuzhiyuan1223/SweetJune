package com.example.sweetjune

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.tools.float_tool_2.FloatToolDC
import com.example.tools.float_tool_2.interfaces.IAppCallback

private const val TAG = "BaseApplicaion"
class BaseApplicaion : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        Log.d(TAG, "onCreate: Base Application context = $mContext")

        //测试代码 初始化FloatToolDC
        if(BuildConfig.IS_DEBUG){
            FloatToolDC.init(this)
            FloatToolDC.addAppCallback(object : IAppCallback{
                override fun onCallback(url: String) {
                    openAppWeb()
                }
            })
        }
    }

    //测试方法
    fun openAppWeb(){
        Toast.makeText(this, "测试打开app内web页", Toast.LENGTH_SHORT).show()
    }

    companion object{
        private var mContext: BaseApplicaion? = null

        @JvmStatic
        fun getInstance() = mContext!!
    }
}