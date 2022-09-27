package com.coderghl.taobaounion.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coderghl.taobaounion.databinding.ListHomePagerContentItemItemBinding
import com.coderghl.taobaounion.databinding.ListLoadingItemBinding
import com.coderghl.taobaounion.model.bean.HomePagerContent
import com.coderghl.taobaounion.utils.LogUtil
import kotlin.collections.ArrayList

class HomePagerContentListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM = 1
        const val LOADING = 0
    }

    private var listData: ArrayList<HomePagerContent.Data> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            ITEM -> ItemHolder(ListHomePagerContentItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> LoadingHolder(ListLoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemHolder -> holder.bindData(listData[position])
        }
    }

    override fun getItemCount() = listData.size + 1

    override fun getItemViewType(position: Int) =
        if (position < listData.size) {
            ITEM
        } else {
            LOADING
        }

    private inner class ItemHolder(private var binding: ListHomePagerContentItemItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: HomePagerContent.Data) {
            binding.titleText.text = data.title

            binding.howMuchText.text = "省:${data.coupon_amount}元"

            binding.originalPriceText.text = "¥${data.zk_final_price}"
            binding.originalPriceText.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

            binding.soldCountText.text = "售出:${data.volume}"


            val finalText = if (data.zk_final_price == null) 0f else data.zk_final_price!!.toFloat()
            val couponAmount = if (data.coupon_amount == null) 0f else data.coupon_amount!!.toString().toFloat()
            val afterCoupon = finalText - couponAmount
            binding.afterCouponText.text = "¥${String.format("%.2f", afterCoupon)}"

            Glide.with(binding.picImage).load("https:${data.pict_url}_200x200.jpg")
                .override(binding.picImage.width, binding.picImage.height)
                .into(binding.picImage)
//            LogUtil.d(this@HomePagerContentListAdapter, "https:${data.pict_url}")
//            LogUtil.d(this@HomePagerContentListAdapter, binding.materialCardView.layoutParams.height.toString())
//            LogUtil.d(this@HomePagerContentListAdapter, binding.materialCardView.layoutParams.width.toString())

        }
    }

    private inner class LoadingHolder(binding: ListLoadingItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    /**
     * 设置列表数据
     */
    fun initListData(newData: List<HomePagerContent.Data>) {
        listData.clear()
        listData.addAll(newData)
        notifyItemRangeChanged(0, listData.size)
    }

    /**
     * 加载更多数据
     */
    fun onLoadMoreData(contents: List<HomePagerContent.Data>) {
        val updateAfterIndex = listData.size
        listData.addAll(contents)
        notifyItemRangeChanged(updateAfterIndex - 1, listData.size)
    }

    /**
     * 刷新数据
     */
    fun onRefreshData(contents: List<HomePagerContent.Data>) {
        listData.addAll(0, contents)
        notifyItemRangeChanged(0, contents.size)
    }
}