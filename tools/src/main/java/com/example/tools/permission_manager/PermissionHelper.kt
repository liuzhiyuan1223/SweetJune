package com.example.tools.permission_manager

import android.app.AppOpsManager
import android.content.Context
import android.os.Binder
import android.os.Build
import android.provider.Settings

class PermissionHelper {

    companion object{
        @JvmStatic //验证app是否被授予悬浮窗权限 通常只有6.0（>23）以上需要判断权限
        fun hasOverLayPermission(context : Context) : Boolean{
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Settings.canDrawOverlays(context)
            } else {
                return hasPermissionBelowMarshmallow(context)
            }
        }

        private fun hasPermissionBelowMarshmallow(context: Context): Boolean {
            return try {
                val manager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                val dispatchMethod = AppOpsManager::class.java.getMethod("checkOp", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, String::class.java)
                //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
                AppOpsManager.MODE_ALLOWED ==
                        dispatchMethod.invoke(manager, 24, Binder.getCallingUid(), context.applicationContext.packageName
                        ) as Int
            } catch (e: Exception) {
                false
            }
        }
    }
}