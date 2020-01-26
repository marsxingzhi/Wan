package com.abyte.wan.rank

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.abyte.core.di.ViewModelFactory
import com.abyte.wan.R
import com.abyte.wan.core.base.ui.BaseActivity
import com.abyte.wan.rank.adapter.RankListAdapter
import com.abyte.wan.rank.model.RankPage
import com.abyte.wan.rank.vm.RankViewModel
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_rank.*
import javax.inject.Inject

class RankActivity : BaseActivity() {

    private lateinit var rankViewModel: RankViewModel
    private lateinit var rankListAdapter: RankListAdapter

    private var currentPage = PAGE_START

    @Inject
    lateinit var mFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)
        rankViewModel = ViewModelProviders.of(this, mFactory).get(RankViewModel::class.java)

        rankListAdapter = RankListAdapter()
        recyclerView.adapter = LuRecyclerViewAdapter(rankListAdapter)
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setOnLoadMoreListener {
            rankViewModel.loadMoreData(currentPage + 1)
        }
        initData()
        observerData()
    }

    private fun observerData() {
        rankViewModel.apply {
            getRankPageData().observe(this@RankActivity, Observer {
                it?.let {
                    currentPage = it.curPage
                    if (it.curPage == 1) {
                        onDataInit(it)
                    } else {
                        onDataLoadMore(it)
                    }
                }
            })
            getErrorResult().observe(this@RankActivity, Observer {
                it?.let {

                }
            })
        }
    }

    private fun onDataLoadMore(rankPage: RankPage) {
        rankListAdapter.data.update(rankPage.datas)
        recyclerView.setNoMore(rankPage.over)
        recyclerView.refreshComplete(rankPage.size)
    }

    private fun onDataInit(rankPage: RankPage) {
        rankListAdapter.data.clear()
        rankListAdapter.data.addAll(rankPage.datas)
        recyclerView.setNoMore(rankPage.over)
        recyclerView.refreshComplete(rankPage.size)
    }

    private fun initData() {
        rankViewModel.getRankList(currentPage)
    }


    companion object {

        const val PAGE_START = 1

        fun startRankActivity(activity: BaseActivity) {
            val intent = Intent(activity, RankActivity::class.java)
            activity.startActivity(intent)
        }
    }
}