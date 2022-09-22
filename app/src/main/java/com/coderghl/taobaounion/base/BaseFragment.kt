package com.coderghl.taobaounion.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.coderghl.taobaounion.R
import com.coderghl.taobaounion.enum.NetworkState
import com.coderghl.taobaounion.utils.LogUtils
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    /**获取绑定布局对象*/
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    /**
     * 当前页面网络状态
     * */
    private var currentPageNetworkState = NetworkState.LOADING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        javaClass.genericSuperclass.also { type ->
            if (type is ParameterizedType) {
                val clazz = type.actualTypeArguments[0] as Class<VB>
                val method = clazz.getMethod("inflate", LayoutInflater::class.java)
                _binding = method.invoke(null, layoutInflater) as VB
            }
        }
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        loadData()
        onViewCreated()
    }

    /**
     * 注册创建视图回调
     */
    abstract fun onViewCreated()

    /** 创建Presenter */
    open fun initPresenter() {}

    /**
     * 请求数据方法
     */
    open fun loadData() {}

    /**
     * 释放资源
     * */
    open fun release() {}

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
        LogUtils.d(this, currentPageNetworkState.toString())
        when (currentPageNetworkState) {
            NetworkState.LOADING -> container.addView(getLoadingView(container))
            NetworkState.NO_NETWORK -> {
                container.removeViewAt(container.children.toList().size - 1)
                container.addView(getNoNetworkView(container))
                // 无网络点击图标重新去请求尝试
                container.children.last().setOnClickListener {
                    LogUtils.d(this, "retry...")
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
                    LogUtils.d(this, "retry...")
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


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
        release()
    }
}