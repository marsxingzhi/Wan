package com.abyte.wan.core.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.abyte.wan.core.base.model.FragmentPage

class CommonStatePagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    val fragmentPageData = ViewPageAdapterList<FragmentPage>(this)


    override fun getItem(position: Int): Fragment = fragmentPageData[position].fragment

    override fun getCount(): Int = fragmentPageData.size

    override fun getItemPosition(fragment: Any): Int {
        for ((index, page) in fragmentPageData.withIndex()) {
            if (fragment == page.fragment) {
                return index
            }
        }
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? = fragmentPageData[position].title
}