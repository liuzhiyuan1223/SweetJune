//package com.example.sweetjune
//
//import android.app.Application
//import android.content.Context
//import android.util.Log
//
//private const val TAG = "BaseApplicaion"
//class BaseApplicaion : Application() {
//
//    override fun onCreate() {
//        super.onCreate()
//        mContext = this
//        Log.d(TAG, "onCreate: Base Application context = $mContext")
//    }
//
//    companion object{
//        private var mContext: BaseApplicaion? = null
//
//        @JvmStatic
//        fun getInstance() = mContext!!
//    }
//}