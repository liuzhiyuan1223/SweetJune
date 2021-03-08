package com.example.sweetjune

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tools.float_tool.FloatTools
import com.example.tools.float_tool.fun_1.IStartWebListener
import kotlinx.android.synthetic.main.activity_main.*

class ExampleMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1.注意申请悬浮窗权限1.manifest文件声明;2.代码中动态获取

        //2.创建点击功能集合
        mTvStartFloatView.setOnClickListener {
            //初始化悬浮窗, 设置初始数据
            FloatTools.get().apply {
                this.init(BaseApplicaion.getInstance()) //初始化操作
                this.createFloateView(this@ExampleMainActivity, object : IStartWebListener {
                    override fun onStartWebClick(url: String) = startWeb(url)
                })
            }
        }
    }

    //测试代码 启动app内web页
    private fun startWeb(url: String) {

    }
}