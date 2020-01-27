package com.abyte.wan.core.base.ui

import android.os.Bundle
import android.view.View
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonPagerAdapter
import com.abyte.wan.core.base.model.FragmentPage
import com.abyte.wan.main.MainActivity
import kotlinx.android.synthetic.main.fragment_common.*

abstract class CommonPagerFragment : BaseFragment() {

    private val viewPageAdapter by lazy {
        CommonPagerAdapter(childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonViewPager.adapter = viewPageAdapter
        

        (activity as MainActivity).actionBarController.setupWithViewPager(commonViewPager)
        viewPageAdapter.fragmentPageData.addAll(getFragmentPages())
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }


    abstract fun getFragmentPages(): List<FragmentPage>

}