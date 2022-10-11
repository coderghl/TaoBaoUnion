package com.coderghl.taobaounion.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.viewbinding.ViewBinding
import com.coderghl.taobaounion.R
import com.coderghl.taobaounion.enum.NetworkState
import com.coderghl.taobaounion.enum.StatusIconMode
import com.coderghl.taobaounion.utils.LogUtil
import com.coderghl.taobaounion.utils.ToastUtil
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    companion object {
        //** 应用是否正在运行 *//
        var appRunning = false
    }

    //** 获取绑定布局对象 *//
    lateinit var binding: VB

    /**
     * 当前网络状态
     */
    private var currentPageNetworkState = NetworkState.LOADING


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

        setStatusBarColor(resources.getColor(R.color.purple_500), StatusIconMode.NIGHT)

        loadData()
        initPresenter()
        onCreate()
    }

    /**
     * 设置沉浸式状态栏
     * */
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


    abstract fun initPresenter()

    /**
     * 获取空页
     */
    private fun getEmptyView(container: ViewGroup): View =
        layoutInflater.inflate(R.layout.on_empty_layout, container, false)

    /**
     * 获取加载页
     */
    private fun getLoadingView(container: ViewGroup): View =
        layoutInflater.inflate(R.layout.on_loading_layout, container, false)

    /**
     * 获取网络请求错误页
     */
    private fun getErrorView(container: ViewGroup): View =
        layoutInflater.inflate(R.layout.on_error_layout, container, false)

    /**
     * 获取无网络页面
     */
    private fun getNoNetworkView(container: ViewGroup): View =
        layoutInflater.inflate(R.layout.on_no_network_layout, container, false)

    /**
     * 根据网络状态切换页面
     */
    open fun switchState(networkState: NetworkState, container: ViewGroup) {
        currentPageNetworkState = networkState
        when (currentPageNetworkState) {
            NetworkState.LOADING -> container.addView(getLoadingView(container))
            NetworkState.NO_NETWORK -> {
                container.removeViewAt(container.children.toList().size - 1)
                container.addView(getNoNetworkView(container))
                // 无网络点击图标重新去请求尝试
                container.children.last().setOnClickListener {
                    LogUtil.d(this, "retry...")
                    retryTap()
                    container.removeViewAt(container.children.toList().size - 1)
                }
            }
            NetworkState.EMPTY -> {
                container.removeViewAt(container.children.toList().size - 1)
                container.addView(getEmptyView(container))
            }
            NetworkState.ERROR -> {
                container.removeViewAt(container.children.toList().size - 1)
                container.addView(getErrorView(container))
                // 请求错误点击图标重新去请求尝试
                container.children.last().setOnClickListener {
                    LogUtil.d(this, "retry...")
                    retryTap()
                    container.removeViewAt(container.children.toList().size - 1)
                }
            }
            else -> {
                container.removeViewAt(container.children.toList().size - 1)
            }
        }
    }


    /**
     * 无网络点击重试请求网络
     */
    open fun retryTap() {}

    /**
     * 注册创建回调
     * */
    abstract fun onCreate()

    /**
     * 请求数据方法
     */
    open fun loadData() {}

    /**
     * 页面销毁
     */
    override fun onDestroy() {
        release()
        super.onDestroy()
    }

    /**
     * 释放资源
     */
    abstract fun release()

    /**
     * 提示弹窗
     */
    open fun showToast(message: String) {
        ToastUtil.showToast(this, message)
    }
}