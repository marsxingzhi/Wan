package com.abyte.core.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs


class SwipeRefreshLayoutCompat @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    // 是否是左右滑动事件
    private var isLeftToRight: Boolean = false
    private var mStartX = 0f
    private var mStartY = 0f


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    mStartX = ev.x
                    mStartY = ev.y
                    // 默认是上下滑动的
                    isLeftToRight = false
                }
                MotionEvent.ACTION_MOVE -> {
                    // 如果是左右滑动事件，则返回false；表示SwipeRefreshLayout不拦截事件
                    if (isLeftToRight) {
                        return false
                    }
                    val currentX = ev.x
                    val currentY = ev.y
                    // 计算手指滑动计算
                    val distanceX = abs(currentX - mStartX)
                    val distanceY = abs(currentY - mStartY)
                    // 如果在X轴方向上的移动距离大于Y轴的最小距离，同时在X轴方向上的移动距离大于在Y轴方向上的移动距离
                    if (distanceX > mTouchSlop && distanceX > distanceY) {
                        isLeftToRight = true
                        return false
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> isLeftToRight = false
            }
            return super.onInterceptTouchEvent(ev)
        } ?: run {
            return super.onInterceptTouchEvent(ev)
        }
    }
}