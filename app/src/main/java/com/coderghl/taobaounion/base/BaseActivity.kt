package com.coderghl.taobaounion.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewbinding.ViewBinding
import com.coderghl.taobaounion.enum.StatusIconMode
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    companion object {
        //** 应用是否正在运行 *//
        var appRunning = false
    }

    //** 获取绑定布局对象 *//
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appRunning = true

        javaClass.genericSuperclass.also { type ->
            if (type is ParameterizedType) {
                val clazz = type.actualTypeArguments[0] as Class<VB>
                val method = clazz.getMethod("inflate", LayoutInflater::class.java)
                binding = method.invoke(null, layoutInflater) as VB
                setContentView(binding.root)
            }
        }

        setStatusBarColor()
        onCreate()
    }

    //** 设置沉浸式状态栏 *//
    open fun setStatusBarColor(
        color: Int = Color.TRANSPARENT,
        iconMode: StatusIconMode = StatusIconMode.DAY_NIGHT
    ) {
        val controller = ViewCompat.getWindowInsetsController(window.decorView)
        when (iconMode) {
            StatusIconMode.DAY_NIGHT -> controller?.let {
                it.isAppearanceLightNavigationBars = true
                it.isAppearanceLightStatusBars = true
            }

            StatusIconMode.NIGHT -> controller?.let {
                it.isAppearanceLightNavigationBars = false
                it.isAppearanceLightStatusBars = false
            }

        }
        window.statusBarColor = color
    }


    abstract fun onCreate()
}