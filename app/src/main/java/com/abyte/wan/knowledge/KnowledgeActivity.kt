package com.abyte.wan.knowledge

import android.content.Intent
import android.os.Bundle
import com.abyte.core.ext.log
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonStatePagerAdapter
import com.abyte.wan.core.base.model.FragmentPage
import com.abyte.wan.core.base.ui.BaseActivity
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.knowledge.view.ActionBarController
import kotlinx.android.synthetic.main.activity_knowledge.*
import java.util.ArrayList

class KnowledgeActivity : BaseActivity() {

    private val actionBarController by lazy {
        ActionBarController(this)
    }

    private val viewPageAdapter by lazy {
        CommonStatePagerAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge)
        knowledgeViewPager.adapter = viewPageAdapter
        actionBarController.setupWithViewPager(knowledgeViewPager)

        val chapterData = intent?.getParcelableExtra<ChapterData>(KEY_CHAPTER_DATA)
        createFragments(chapterData?.children)
    }

    private fun createFragments(list: ArrayList<ChapterData?>?) {
        list?.let {
            val arr = ArrayList<FragmentPage>()
            for (chapterData in list) {
                if (chapterData == null) {
                    continue
                }
                log("KnowledgeActivity---createFragments---name = ${chapterData.name}")
                arr.add(
                    FragmentPage(
                        KnowledgeArticlesFragment.newInstance(chapterData.id),
                        chapterData.name
                    )
                )
            }
            viewPageAdapter.fragmentPageData.addAll(arr)
        }
    }


    companion object {

        private const val KEY_CHAPTER_DATA = "key_chapter_data"

        fun startKnowledgeActivity(activity: BaseActivity, chapterData: ChapterData) {
            val intent = Intent(activity, KnowledgeActivity::class.java)
            intent.putExtra(KEY_CHAPTER_DATA, chapterData)
            activity.startActivity(intent)
        }
    }
}