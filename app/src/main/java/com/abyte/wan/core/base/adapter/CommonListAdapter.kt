package com.abyte.wan.core.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.sdk15.listeners.onClick

abstract class CommonListAdapter<T>(@LayoutRes val itemResId: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    val data = AdapterList<T>(this)


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(itemResId, parent, false)
        return CommonViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindData(holder, data[position])
        holder.itemView.onClick {
            onItemClick(holder.itemView, data[position])
        }
    }

    abstract fun onItemClick(itemView: View, item: T)

    abstract fun bindData(viewHolder: RecyclerView.ViewHolder, item: T)

    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}