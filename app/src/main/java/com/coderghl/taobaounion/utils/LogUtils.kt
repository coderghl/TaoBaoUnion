package com.coderghl.taobaounion.utils

import android.util.Log

object LogUtils {
    private const val DEBUG_LEVEL = 4
    private const val INFO_LEVEL = 3
    private const val WARNING_LEVEL = 2
    private const val ERROR_LEVEL = 1

    private const val currentLevel = 4


    fun d(clazz: Any, msg: String) {
        if (currentLevel >= DEBUG_LEVEL) Log.d(clazz.javaClass.name, msg)
    }

    fun i(clazz: Any, msg: String) {
        if (currentLevel >= INFO_LEVEL) Log.i(clazz.javaClass.name, msg)
    }

    fun w(clazz: Any, msg: String) {
        if (currentLevel >= WARNING_LEVEL) Log.w(clazz.javaClass.name, msg)
    }

    fun e(clazz: Any, msg: String) {
        if (currentLevel >= ERROR_LEVEL) Log.e(clazz.javaClass.name, msg)
    }
}