package com.example.tools.float_tool

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.tools.float_tool.fun_1.IStartWebListener
import com.example.tools.float_tool.fun_1.StartWebFragment
import com.example.tools.model.FunClickModel

class FunctionConfig {

    fun buildFunConfig(activity: FragmentActivity, listener : IStartWebListener) : ArrayList<FunClickModel>{
        val funClickModels = ArrayList<FunClickModel>()

        //1.初始化第一个功能
        val fun1 = FunClickModel("打开端内H5", object : View.OnClickListener {
            override fun onClick(v: View?) {
                //1.启动一个承载输入http地址页面的Fragment
                val startWebFragment = StartWebFragment()
                //2.给Fragment设置一个回调，用于通过端上context及端上HybridActivity启动该http地址页面、
                startWebFragment.setIStartWebListener(listener)

                //3.启动该Fragment
                activity.supportFragmentManager.beginTransaction()
                        .add(startWebFragment, startWebFragment.javaClass.simpleName)
                        .commitAllowingStateLoss()
            }
        })
        funClickModels.add(fun1)

        //2.初始化第二个功能


        return funClickModels
    }
}