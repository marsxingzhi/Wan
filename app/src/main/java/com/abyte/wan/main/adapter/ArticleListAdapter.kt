package com.abyte.wan.main.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abyte.core.ext.log
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonListAdapter
import com.abyte.wan.main.model.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleListAdapter : CommonListAdapter<Article>(R.layout.item_article) {

    override fun onItemClick(itemView: View, article: Article) {
        log("ArticleAdapter---onItemClick")
    }

    override fun bindData(viewHolder: RecyclerView.ViewHolder, article: Article) {
        log("ArticleAdapter---bindData")
        viewHolder.itemView.apply {
            articleNew.visibility = if (article.fresh) View.VISIBLE else View.GONE
            log("article-author = ${article.author}")
            author.text = article.author.takeIf { !TextUtils.isEmpty(it) } ?: article.shareUser
            if (article.tags != null && article.tags!!.size > 0) {
                articleTag.visibility = View.VISIBLE
                articleTag.text = article.tags!![0]?.name
            } else {
                articleTag.visibility = View.GONE
            }
            articleTime.text = article.niceDate
            if (!TextUtils.isEmpty(article.envelopePic)) {
                // TODO("加载图片")
                articleImg.visibility = View.VISIBLE
            } else {
                articleImg.visibility = View.GONE
            }
            articleTitle.text = Html.fromHtml(article.title)
            if (!TextUtils.isEmpty(article.desc)) {
                articleDesc.visibility = View.VISIBLE
                articleDesc.text = article.desc.trim()
                articleTitle.isSingleLine = true
            } else {
                articleDesc.visibility = View.GONE
                articleTitle.isSingleLine = false
            }
            articleTagTop.visibility = if (article.top) View.VISIBLE else View.GONE
            articleChapter.text =
                Html.fromHtml(formatChapterName(article.superChapterName, article.chapterName))
        }
    }

    private fun formatChapterName(vararg names: String): String {
        return StringBuilder().apply {
            for (name in names) {
                if (!TextUtils.isEmpty(name)) {
                    if (this.isNotEmpty()) {
                        this.append(".")
                    }
                    this.append(name)
                }
            }
        }.toString()
    }
}