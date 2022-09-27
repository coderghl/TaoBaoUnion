package com.coderghl.taobaounion.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null
    fun showToast(context: Context, message: String) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(message)
        }
        toast?.show()
    }
}