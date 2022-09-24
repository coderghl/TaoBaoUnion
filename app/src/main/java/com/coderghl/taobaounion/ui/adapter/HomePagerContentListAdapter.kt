package com.coderghl.taobaounion.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coderghl.taobaounion.databinding.ListHomePagerContentItemItemBinding
import com.coderghl.taobaounion.model.bean.HomePagerContent
import kotlin.collections.ArrayList

class HomePagerContentListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listData: ArrayList<HomePagerContent.Data> = ArrayList()
    private var bannerData: ArrayList<HomePagerContent.Data> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val listBinding = ListHomePagerContentItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(listBinding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemHolder -> holder.bindData(listData[position])
        }
    }

    override fun getItemCount() = listData.size


    inner class ItemHolder(private var binding: ListHomePagerContentItemItemBinding) : RecyclerView.ViewHolder(binding.root) {

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

            Glide.with(binding.picImage).load("https:${data.pict_url}").override(binding.picImage.width, binding.picImage.height)
                .into(binding.picImage)
        }
    }

    /**
     * 设置列表数据
     */
    fun initListData(newData: List<HomePagerContent.Data>) {
        listData.clear()
        listData.addAll(newData)
        notifyItemRangeChanged(1, listData.size)
    }
}