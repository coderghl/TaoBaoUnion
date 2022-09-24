package com.coderghl.taobaounion.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coderghl.taobaounion.databinding.PagerBannerItemItemBinding
import com.coderghl.taobaounion.model.bean.HomePagerContent

class HomeBannerAdapter(private var data: ArrayList<HomePagerContent.Data> = ArrayList()) :
    RecyclerView.Adapter<HomeBannerAdapter.InnerHolder>() {

    inner class InnerHolder(private var binding: PagerBannerItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomePagerContent.Data) {
            Glide.with(binding.picImage).load("https:${data.pict_url}").override(binding.picImage.width, binding.picImage.height)
                .into(binding.picImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        return InnerHolder(PagerBannerItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size


    /**
     * 初始化轮播图数据
     */
    fun initBannerData(newData: List<HomePagerContent.Data>) {
        data.clear()
        data.addAll(newData)
        notifyItemRangeChanged(0, data.size)
    }
}