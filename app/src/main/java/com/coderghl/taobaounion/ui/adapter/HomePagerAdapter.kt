package com.coderghl.taobaounion.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.coderghl.taobaounion.model.bean.HomeCategories
import com.coderghl.taobaounion.ui.fragment.homefragment.HomePagerContentFragment

class HomePagerAdapter(fragmentActivity: FragmentActivity) :
    androidx.viewpager2.adapter.FragmentStateAdapter(fragmentActivity) {

    private var data = ArrayList<HomeCategories.Data>()

    override fun getItemCount() = data.size

    override fun createFragment(position: Int): Fragment {
        return HomePagerContentFragment(data[position])
    }

    fun setData(homeCategories: HomeCategories) {
        data.clear()
        data.addAll(homeCategories.data)
        notifyItemRangeChanged(0, data.size)
    }
}