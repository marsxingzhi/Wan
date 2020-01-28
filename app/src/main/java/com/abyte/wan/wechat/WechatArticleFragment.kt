package com.abyte.wan.wechat

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
import com.abyte.wan.wechat.vm.WechatViewModel
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_articles_page.*
import org.jetbrains.anko.support.v4.onRefresh
import javax.inject.Inject

class WechatArticleFragment : BaseFragment() {

    private lateinit var wechatViewModel: WechatViewModel
    private lateinit var articleListAdapter: ArticleListAdapter
    private var authorId = 0

    private var currentPage = 1

    @Inject
    lateinit var mFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wechatViewModel = ViewModelProviders.of(this, mFactory).get(WechatViewModel::class.java)

        refreshView.setColorSchemeResources(
            R.color.google_red,
            R.color.google_yellow,
            R.color.google_green,
            R.color.google_blue
        )
        articleListAdapter = ArticleListAdapter()
        recyclerView.apply {
            adapter = LuRecyclerViewAdapter(articleListAdapter)
            setLoadMoreEnabled(true)
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setOnLoadMoreListener {
                wechatViewModel.loadMoreData(authorId, currentPage + 1)
            }
        }
        refreshView.isRefreshing = true
        refreshView.onRefresh {
            wechatViewModel.refreshData(authorId)
        }
        initData()
        observerData()
    }

    private fun observerData() {
        wechatViewModel.getWechatAuthorArticlesResult().observe(this, Observer {
            it?.let {
                currentPage = it.curPage
                if (it.curPage == 1) {
                    onDataInit(it)
                } else if (it.curPage > 1) {
                    onDataLoadMore(it)
                }
            }
        })
        wechatViewModel.getErrorResult().observe(this, Observer {
            it?.let {

            }
        })
    }

    private fun onDataLoadMore(articlePage: ArticlePage) {
        articleListAdapter.data.update(articlePage.datas)
        recyclerView.setNoMore(articlePage.over)
        recyclerView.refreshComplete(articlePage.size)
    }

    private fun onDataInit(articlePage: ArticlePage) {
        articleListAdapter.data.clear()
        articleListAdapter.data.addAll(articlePage.datas)
        recyclerView.setNoMore(articlePage.over)
        recyclerView.refreshComplete(articlePage.size)
        refreshView.isRefreshing = false
    }

    private fun initData() {
        authorId = arguments?.getInt(KEY_AUTHOR_ID, 0)!!
        wechatViewModel.getWechatArticlesOfAuthor(authorId, currentPage)
    }

    override fun getLayoutId(): Int = R.layout.fragment_articles_page


    companion object {

        const val KEY_AUTHOR_ID = "author_id"

        fun newInstance(authorId: Int) = WechatArticleFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_AUTHOR_ID, authorId)
            }
        }
    }
}