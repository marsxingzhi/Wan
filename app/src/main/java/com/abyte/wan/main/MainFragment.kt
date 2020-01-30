package com.abyte.wan.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.abyte.core.di.ViewModelFactory
import com.abyte.wan.R
import com.abyte.wan.core.base.ui.BaseFragment
import com.abyte.wan.main.adapter.ArticleListAdapter
import com.abyte.wan.main.model.ArticlePage
import com.abyte.wan.main.vm.MainViewModel
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import kotlinx.android.synthetic.main.main_fragment_home.*
import org.jetbrains.anko.support.v4.onRefresh
import javax.inject.Inject

class MainFragment : BaseFragment() {


    private lateinit var mainViewModel: MainViewModel

    private lateinit var articleListAdapter: ArticleListAdapter

    private var currentPage = PAGE_START

    @Inject
    lateinit var mFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 首页不展示TabLayout
        (activity as MainActivity).actionBarController.setupWithViewPager(null)

        mainViewModel = ViewModelProviders.of(this, mFactory).get(MainViewModel::class.java)

        refreshView.setColorSchemeResources(
            R.color.google_red,
            R.color.google_yellow,
            R.color.google_green,
            R.color.google_blue
        )

        articleListAdapter = ArticleListAdapter(activity as MainActivity)
        recyclerView.adapter = LuRecyclerViewAdapter(articleListAdapter)
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setOnLoadMoreListener {
            mainViewModel.loadMoreData(currentPage)
        }
        refreshView.isRefreshing = true
        refreshView.onRefresh {
            mainViewModel.refreshData()
        }
        initData()
        observerData()
    }

    private fun observerData() {
        mainViewModel.apply {
            getArticlePages().observe(this@MainFragment, Observer {
                it?.let {
                    currentPage = it.curPage + PAGE_START
                    if (it.curPage == 1) {
                        onDataInit(it)
                    } else {
                        onDataLoadMore(it)
                    }
                }
            })

            getErrorResult().observe(this@MainFragment, Observer {
                it?.let {

                }
            })
        }
    }

    private fun onDataLoadMore(item: ArticlePage) {
        articleListAdapter.data.update(item.datas)
        recyclerView.setNoMore(item.over)
        recyclerView.refreshComplete(item.size)
    }

    private fun onDataInit(item: ArticlePage) {
        // 第一页
        articleListAdapter.data.clear()
        articleListAdapter.data.addAll(item.datas)
        recyclerView.setNoMore(item.over)
        recyclerView.refreshComplete(item.size)
        refreshView.isRefreshing = false
    }

    private fun initData() {
        mainViewModel.getArticlePages(currentPage)
    }


    override fun getLayoutId(): Int = R.layout.main_fragment_home


    companion object {

        const val PAGE_START = 0
    }
}