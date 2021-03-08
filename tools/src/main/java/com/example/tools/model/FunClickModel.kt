package com.example.tools.model

import android.view.View

data class FunClickModel(
        val funName: String,
        val clickListener: View.OnClickListener
)