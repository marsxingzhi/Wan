package com.abyte.wan.project

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
import com.abyte.wan.project.vm.ProjectViewModel
import kotlinx.android.synthetic.main.main_fragment_wechat.*
import javax.inject.Inject

class ProjectFragment : BaseFragment() {


    private lateinit var projectViewModel: ProjectViewModel

    @Inject
    lateinit var mFactory: ViewModelFactory


    private val viewPageAdapter by lazy {
        CommonStatePagerAdapter(childFragmentManager)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectViewModel = ViewModelProviders.of(this, mFactory).get(ProjectViewModel::class.java)
        wechatViewPager.adapter = viewPageAdapter
        (activity as MainActivity).actionBarController.setupWithViewPager(wechatViewPager)
        initData()
        observerData()
    }

    private fun observerData() {
        projectViewModel.getProjectTabsResult().observe(this, Observer {
            it?.let {
                createFragments(it)
            }
        })

        projectViewModel.getErrorResult().observe(this, Observer {
            it?.let {

            }
        })
    }

    private fun createFragments(list: List<ChapterData>) {
        val arr = ArrayList<FragmentPage>()
        for (chapterData in list) {
            log("ProjectFragment---createFragments---name = ${chapterData.name}")
            arr.add(FragmentPage(ProjectArticleFragment(), chapterData.name))
        }
        viewPageAdapter.fragmentPageData.addAll(arr)
    }

    private fun initData() {
        projectViewModel.getProjectTabs()
    }

    override fun getLayoutId(): Int = R.layout.main_fragment_wechat

}