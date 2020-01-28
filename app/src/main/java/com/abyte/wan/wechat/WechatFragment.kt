package com.abyte.wan.wechat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abyte.core.di.ViewModelFactory
import com.abyte.core.ext.log
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonStatePagerAdapter
import com.abyte.wan.core.base.model.FragmentPage
import com.abyte.wan.core.base.ui.BaseFragment
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.main.MainActivity
import com.abyte.wan.wechat.vm.WechatViewModel
import kotlinx.android.synthetic.main.main_fragment_wechat.*
import javax.inject.Inject

class WechatFragment : BaseFragment() {


    private lateinit var wechatViewModel: WechatViewModel

    @Inject
    lateinit var mFactory: ViewModelFactory


    private val viewPageAdapter by lazy {
        CommonStatePagerAdapter(childFragmentManager)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wechatViewModel = ViewModelProviders.of(this, mFactory).get(WechatViewModel::class.java)
        wechatViewPager.adapter = viewPageAdapter
        (activity as MainActivity).actionBarController.setupWithViewPager(wechatViewPager)
        initData()
        observerData()
    }

    private fun observerData() {
        wechatViewModel.getWechatArticlesResult().observe(this, Observer {
            it?.let {
                createFragments(it)
            }
        })
        wechatViewModel.getErrorResult().observe(this, Observer {
            it?.let {

            }
        })
    }

    private fun createFragments(list: List<ChapterData>) {
        val arr = ArrayList<FragmentPage>()
        for (chapterData in list) {
            log("WechatFragment---createFragments---name = ${chapterData.name}")
            arr.add(FragmentPage(WechatArticleFragment.newInstance(chapterData.id), chapterData.name))
        }
        viewPageAdapter.fragmentPageData.addAll(arr)
    }

    private fun initData() {
        wechatViewModel.getWechatArticles()
    }

    override fun getLayoutId(): Int = R.layout.main_fragment_wechat

}