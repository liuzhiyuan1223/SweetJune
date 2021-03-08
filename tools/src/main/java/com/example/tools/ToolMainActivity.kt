package com.example.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ToolMainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun actionStart(context: Context) { //外部启动ToolMainActivity
            context.startActivity(Intent(context, ToolMainActivity::class.java).apply {
                if(context !is Activity) this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_main)
    }
}