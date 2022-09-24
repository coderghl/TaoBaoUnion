package com.coderghl.taobaounion.utils

import android.content.Context


fun Int.dp2px(context: Context) = context.resources.displayMetrics.density * this

fun Int.px2dp(context: Context) = this / context.resources.displayMetrics.density
