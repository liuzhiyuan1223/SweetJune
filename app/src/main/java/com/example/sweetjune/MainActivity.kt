package com.example.sweetjune

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.tools.float_tool.FloatUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 注意申请悬浮窗权限1.manifest文件声明;2.代码中动态获取
         */

        val mTvStartFloatView = findViewById<TextView>(R.id.mTvStartFloatView)
        mTvStartFloatView.setOnClickListener {
            val floatUtil = FloatUtil(this)
            floatUtil.createFloateView()
        }
    }
}