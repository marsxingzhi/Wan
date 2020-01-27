package com.abyte.wan.knowledge.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abyte.core.ext.log
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonCardListAdapter
import com.abyte.wan.knowledge.model.ChapterData
import kotlinx.android.synthetic.main.layout_item_system_tree.view.*

class ChapterListAdapter : CommonCardListAdapter<ChapterData>(R.layout.layout_item_system_tree) {

    override fun onItemClick(itemView: View, item: ChapterData) {
    }

    override fun bindData(viewHolder: RecyclerView.ViewHolder, item: ChapterData) {
        viewHolder.itemView.apply {
            log("ChapterListAdapter---chapterData name = ${item.name}")
            tvChapterName.text = item.name
        }
    }

}