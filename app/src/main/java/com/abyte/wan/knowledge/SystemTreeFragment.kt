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
import com.abyte.wan.knowledge.adapter.ChapterListAdapter
import com.abyte.wan.knowledge.vm.KnowledgeViewModel
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import kotlinx.android.synthetic.main.frargment_system_tree.*
import javax.inject.Inject

class SystemTreeFragment : BaseFragment() {

    private lateinit var knowledgeViewModel: KnowledgeViewModel

    private lateinit var chapterListAdapter: ChapterListAdapter


    @Inject
    lateinit var mFactory: ViewModelFactory


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("SystemTreeFragment")
        knowledgeViewModel =
            ViewModelProviders.of(this, mFactory).get(KnowledgeViewModel::class.java)

        chapterListAdapter = ChapterListAdapter()
        systemRecycler.adapter = LuRecyclerViewAdapter(chapterListAdapter)
        systemRecycler.setLoadMoreEnabled(false)
        systemRecycler.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        systemRecycler.itemAnimator = DefaultItemAnimator()
        initData()

        observerData()
    }

    private fun observerData() {
        knowledgeViewModel.getChapterDataResult().observe(this, Observer {
            log("SystemTree---success---it = $it")
            it?.let {
                chapterListAdapter.data.clear()
                chapterListAdapter.data.addAll(it)
                systemRecycler.refreshComplete(it.size)
            }
        })

        knowledgeViewModel.getErrorResult().observe(this, Observer {
            log("SystemTree---error---it = $it")
        })
    }


    private fun initData() {
        knowledgeViewModel.getChapterList()
    }

    override fun getLayoutId(): Int {
        return R.layout.frargment_system_tree
    }


}