package com.abyte.wan.knowledge.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abyte.core.ext.log
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonCardListAdapter
import com.abyte.wan.knowledge.model.ChapterData
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.layout_item_system_tree.view.*
import java.util.*

class ChapterListAdapter : CommonCardListAdapter<ChapterData>(R.layout.layout_item_system_tree) {

    private var layoutInflater: LayoutInflater? = null

    private val mFlexItemTextViewCaches = LinkedList<TextView>()

    override fun onItemClick(itemView: View, item: ChapterData) {
    }

    override fun bindData(viewHolder: RecyclerView.ViewHolder, item: ChapterData) {
        viewHolder.itemView.apply {
            log("ChapterListAdapter---chapterData name = ${item.name}")
            tvChapterName.text = item.name
            if (item.children != null && item.children!!.size > 0) {
                for (i in 0 until item.children!!.size) {
                    flexBox.addView(getFlexItemTextView(flexBox).apply {
                        text = item.children!![i]?.name ?: "无数据"
                    })
                }
            }
        }
    }


    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        for (i in 0 until holder.itemView.flexBox.childCount) {
            mFlexItemTextViewCaches.offer(holder.itemView.flexBox.getChildAt(i) as TextView)
        }
        holder.itemView.flexBox.removeAllViews()    // 需要移除所有的子View
    }

    private fun getFlexItemTextView(flexBox: FlexboxLayout): TextView {
        return mFlexItemTextViewCaches.poll().takeIf {
            it != null
        } ?: run {
            createFlexItemView(flexBox)
        }
    }

    private fun createFlexItemView(flexBox: FlexboxLayout): TextView {
        // 这个写法值得借鉴
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(flexBox.context)
        }
        return layoutInflater!!.inflate(
            R.layout.layout_item_flexbox_child,
            flexBox,
            false
        ) as TextView
    }

}