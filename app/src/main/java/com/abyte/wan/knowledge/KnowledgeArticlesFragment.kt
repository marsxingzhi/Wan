package com.abyte.wan.knowledge

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.abyte.core.di.ViewModelFactory
import com.abyte.core.ext.log
import com.abyte.wan.R
import com.abyte.wan.core.base.ui.BaseFragment
import com.abyte.wan.knowledge.vm.KnowledgeViewModel
import com.abyte.wan.main.adapter.ArticleListAdapter
import com.abyte.wan.main.model.ArticlePage
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_articles_page.*
import org.jetbrains.anko.support.v4.onRefresh
import javax.inject.Inject


class KnowledgeArticlesFragment : BaseFragment() {

    private lateinit var knowledgeViewModel: KnowledgeViewModel

    private lateinit var articleListAdapter: ArticleListAdapter

    private var currentPage = PAGE_START

    private var cid = 0

    @Inject
    lateinit var mFactory: ViewModelFactory


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cid = arguments?.getInt(KEY_CID)!!
        log("KnowledgeArticlesFragment---cid = $cid")
        knowledgeViewModel =
            ViewModelProviders.of(this, mFactory).get(KnowledgeViewModel::class.java)

        refreshView.setColorSchemeResources(
            R.color.google_red,
            R.color.google_yellow,
            R.color.google_green,
            R.color.google_blue
        )

        articleListAdapter = ArticleListAdapter(activity as KnowledgeActivity)
        recyclerView.adapter = LuRecyclerViewAdapter(articleListAdapter)
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setOnLoadMoreListener {
            knowledgeViewModel.loadMoreData(currentPage, cid)
        }
        refreshView.isRefreshing = true
        refreshView.onRefresh {
            knowledgeViewModel.refreshData(cid)
        }
        initData()
        observerData()
    }

    private fun observerData() {
        knowledgeViewModel.getKnowledgeArticlesResult().observe(this, Observer {
            it?.let {
                currentPage = it.curPage + PAGE_START
                if (it.curPage == 1) {
                    onDataInit(it)
                } else {
                    onDataLoadMore(it)
                }
            }
        })
        knowledgeViewModel.getErrorResult().observe(this, Observer {
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
        knowledgeViewModel.getKnowledgeArticlesByChildrenId(currentPage, cid)
    }


    override fun getLayoutId(): Int = R.layout.fragment_articles_page

    companion object {

        private const val PAGE_START = 0

        private const val KEY_CID = "key_cid"

        fun newInstance(cid: Int) = KnowledgeArticlesFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_CID, cid)
            }
        }
    }
}