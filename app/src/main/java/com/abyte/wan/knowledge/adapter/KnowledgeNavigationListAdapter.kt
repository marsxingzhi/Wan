package com.abyte.wan.knowledge.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abyte.core.ext.log
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonCardListAdapter
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.knowledge.model.KnowledgeNavigationData
import kotlinx.android.synthetic.main.layout_item_system_tree.view.*

class KnowledgeNavigationListAdapter :
    CommonCardListAdapter<KnowledgeNavigationData>(R.layout.layout_item_system_tree) {

    override fun onItemClick(itemView: View, item: KnowledgeNavigationData) {
    }

    override fun bindData(viewHolder: RecyclerView.ViewHolder, item: KnowledgeNavigationData) {
        viewHolder.itemView.apply {
            log("KnowledgeNavigationListAdapter---KnowledgeNavigationData name = ${item.name}")
            tvChapterName.text = item.name
        }
    }

}