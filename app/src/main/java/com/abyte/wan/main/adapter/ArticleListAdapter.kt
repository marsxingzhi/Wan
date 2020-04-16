package com.abyte.wan.main.adapter

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abyte.core.ext.log
import com.abyte.core.widgets.LikeView
import com.abyte.wan.R
import com.abyte.wan.core.base.adapter.CommonCardListAdapter
import com.abyte.wan.core.base.ui.BaseActivity
import com.abyte.wan.ext.loadWithGlide
import com.abyte.wan.main.model.Article
import com.abyte.wan.web.WebActivity
import kotlinx.android.synthetic.main.item_article.view.*
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.toast
import java.util.*

class ArticleListAdapter constructor(private val context: Context) :
    CommonCardListAdapter<Article>(R.layout.item_article) {

    private lateinit var clickLike: (Boolean) -> Unit

    constructor(context: Context, clickLike: (Boolean) -> Unit) : this(context) {
        this.clickLike = clickLike
    }

    private val random = Random()

    override fun onItemClick(itemView: View, article: Article) {
        log("ArticleAdapter---onItemClick")
        if (!TextUtils.isEmpty(article.link)) {
            WebActivity.startWebActivity(context as BaseActivity, article.link)
        } else {
            context.toast("link为空")
        }
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
                articleImg.loadWithGlide(article.envelopePic)
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

            // TODO，暂时没有收藏数，随机一下
            likeView.setLikeCount(random.nextInt(2020))
            likeView.setLike(article.collect)

            // TODO 这里被拦截了，执行不到
            likeView.onClick {
                clickLike((it as LikeView).isLike())
            }
            // 下面方法会导致栈溢出
//            likeView.setOnItemClickLikeListener(object : LikeView.OnItemClickLikeListener{
//                override fun clickLike(isLike: Boolean) {
//                    clickLike(isLike)
//                }
//            })
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        log("onAttachedToRecyclerView")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        log("onDetachedFromRecyclerView")
    }

    // 当item view attach到window的时候就会调用，这里可以做一些埋点上报
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        log("onViewAttachedToWindow---holder = ${holder.adapterPosition}")
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        log("onViewDetachedFromWindow---holder = ${holder.adapterPosition}")
    }
}