package com.abyte.wan.core.base.adapter

import androidx.viewpager.widget.PagerAdapter

class ViewPageAdapterList<T>(val adapter: PagerAdapter) : ArrayList<T>() {


    override fun add(element: T): Boolean {
        return super.add(element).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun add(index: Int, element: T) {
        super.add(index, element)
        adapter.notifyDataSetChanged()
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return super.addAll(elements).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return super.addAll(index, elements).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun remove(element: T): Boolean {
        return super.remove(element).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun removeAt(index: Int): T {
        return super.removeAt(index).apply {
            adapter.notifyDataSetChanged()
        }
    }


    override fun removeAll(elements: Collection<T>): Boolean {
        return super.removeAll(elements).apply {
            adapter.notifyDataSetChanged()
        }
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        super.removeRange(fromIndex, toIndex).apply {
            adapter.notifyDataSetChanged()
        }
    }

    fun update(elements: Collection<T>) {
        super.addAll(elements)
        adapter.notifyDataSetChanged()
    }
}