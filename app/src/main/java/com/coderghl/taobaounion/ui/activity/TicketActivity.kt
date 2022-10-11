package com.coderghl.taobaounion.ui.activity

import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import com.bumptech.glide.Glide
import com.coderghl.taobaounion.base.BaseActivity
import com.coderghl.taobaounion.databinding.ActivityTicketBinding
import com.coderghl.taobaounion.enum.NetworkState
import com.coderghl.taobaounion.model.bean.TicketResult
import com.coderghl.taobaounion.presenter.ITicketPresenter
import com.coderghl.taobaounion.presenter.impl.TicketPresenterImpl
import com.coderghl.taobaounion.view.ITicketPagerCallback
import java.lang.Exception

class TicketActivity : BaseActivity<ActivityTicketBinding>(), ITicketPagerCallback {
    private var presenter: ITicketPresenter? = null
    private var installedTaobao = false

    private var title: String = ""
    private var coupon_click_url: String = ""
    private var pict_url: String = ""

    override fun initPresenter() {
        presenter = TicketPresenterImpl()
        checkPhoneDownloadTaoBao()
        presenter?.registerCallback(this)
    }

    override fun onCreate() {
        title = intent.getStringExtra("title") ?: ""
        coupon_click_url = intent.getStringExtra("coupon_click_url") ?: ""
        pict_url = intent.getStringExtra("pict_url") ?: ""
        presenter?.getTicket(title, coupon_click_url, pict_url)

        binding.getTicketBtn.text = if (installedTaobao) "打开淘宝领券" else "复制淘口令"

        setListener()
    }

    @SuppressLint("ServiceCast")
    private fun setListener() {
        binding.appbar.setNavigationOnClickListener {
            finish()
        }

        // 复制淘口令到剪切板
        binding.getTicketBtn.setOnClickListener {
            val ticketCode = binding.ticketCode.text.trim().toString()
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("ghl_taobao_ticket_code", ticketCode))
            // 判断是否有淘宝，如果有就打开如果没有就提示复制成功
            if (installedTaobao) {
                val intent = Intent()
                intent.component = ComponentName("com.taobao.taobao", "com.taobao.tao.TBMainActivity")
                startActivity(intent)
            } else {
                showToast("已经复制,粘贴分享或打开淘宝")
            }
        }
    }

    /**
     * 检查手机是否安装了淘宝
     */
    private fun checkPhoneDownloadTaoBao() {
        installedTaobao = try {
            packageManager.getPackageInfo("com.taobao.taobao", PackageManager.MATCH_UNINSTALLED_PACKAGES) != null
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 处理请求到的数据
     */
    private fun handleData(cover: String, result: TicketResult) {
        Glide.with(binding.photo).load("https:${cover}").into(binding.photo)
        binding.ticketCode.setText(result.data.tbk_tpwd_create_response.data.model)
    }

    /**
     * 数据请求成功
     */
    override fun onTicketSuccess(cover: String, result: TicketResult) {
        handleData(cover, result)
    }

    /**
     * 释放资源
     */
    override fun release() {
        presenter?.unRegisterCallback(this)
    }

    /**
     * 正在加载
     */
    override fun onLoading() {
        switchState(NetworkState.LOADING, binding.container)
    }

    /**
     * 数据为空
     */
    override fun onEmpty() {
        switchState(NetworkState.EMPTY, binding.container)
    }

    /**
     * 请求失败
     */
    override fun onError() {
        switchState(NetworkState.ERROR, binding.container)
    }

    /**
     * 无网络链接
     */
    override fun onNoNetwork() {
        switchState(NetworkState.NO_NETWORK, binding.container)
    }

    /**
     * 请求失败时点击重试
     */
    override fun retryTap() {
        presenter?.getTicket(title, coupon_click_url, pict_url)
    }
}