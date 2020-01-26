package com.abyte.wan.core.base.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.abyte.wan.R
import kotlinx.android.synthetic.main.item_card.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk15.listeners.onClick

abstract class CommonCardListAdapter<T>(@LayoutRes val itemResId: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var oldPos = -1

    val data = AdapterList<T>(this)


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        LayoutInflater.from(itemView.context).inflate(itemResId, itemView.contentContainer)
        return CommonCardViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindData(holder, data[position])
        holder.itemView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN ->
                    ViewCompat
                        .animate(holder.itemView)
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .translationZ(holder.itemView.dip(10).toFloat())
                        .duration = CARD_ANIM_DURATION
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    ViewCompat
                        .animate(holder.itemView)
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .translationZ(holder.itemView.dip(10).toFloat())
                        .duration = CARD_ANIM_DURATION
                }
            }
            false
        }
        holder.itemView.onClick {
            onItemClick(holder.itemView, data[position])
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is CommonCardViewHolder && holder.layoutPosition > oldPos) {
            addItemAnimation(holder.itemView)
            oldPos = holder.layoutPosition
        }
    }

    private fun addItemAnimation(itemView: View) {
        ObjectAnimator.ofFloat(itemView, "translationY", 500f, 0f).setDuration(500).start()
    }

    abstract fun onItemClick(itemView: View, item: T)

    abstract fun bindData(viewHolder: RecyclerView.ViewHolder, item: T)

    class CommonCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        const val CARD_ANIM_DURATION = 100L
    }

}