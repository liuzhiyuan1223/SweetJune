package com.example.tools.float_tool_2

import android.content.Context

object LocalStatePref {

    private const val FILE = "floatWindow"
    fun isShowing(ctx: Context): Boolean {
        return ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE).getBoolean("show", false)
    }

    private fun setShowing(ctx: Context, isShowing: Boolean) {
        ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit()
            .putBoolean("show", isShowing)
            .commit()
    }

    fun loadLocationX(ctx: Context): Int {
        return ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE).getInt("x", 0)
    }

    fun loadLocationY(ctx: Context): Int {
        return ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE).getInt("y", 0)
    }

    fun saveLocation(ctx: Context, x: Int, y: Int) {
        ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit()
            .putInt("x", x)
            .putInt("y", y)
            .commit()
    }
}