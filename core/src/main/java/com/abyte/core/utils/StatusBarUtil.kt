package com.abyte.core.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.abyte.core.R

object StatusBarUtil {

    fun hideStatusBar(activity: FragmentActivity?) {
        activity?.let {
            setFullScreenFlags(activity)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                    setStatusBarColor(activity)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN -> {
                    val decorView = activity.window.decorView
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                    val actionBar = activity.actionBar
                    actionBar?.hide()
                }
                else -> {

                }
            }
        }
    }

    private fun setFullScreenFlags(activity: FragmentActivity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun setStatusBarColor(activity: FragmentActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = getStatusBarColor(activity)
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setColor(activity, getStatusBarColor(activity))
        }
    }

    private fun getStatusBarColor(activity: FragmentActivity): Int {
        return activity.resources.getColor(R.color.transparent)
    }


    private fun setColor(activity: FragmentActivity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val statusView = createStatusBar(activity, color)
            val decorView = activity.window.decorView as ViewGroup
            decorView.addView(statusView)
            val content = activity.findViewById<View>(android.R.id.content) as ViewGroup
            val rootView = content.getChildAt(content.childCount - 1) as ViewGroup
            rootView.fitsSystemWindows = true
            rootView.clipToPadding = true
        }
    }

    private fun createStatusBar(activity: Activity, color: Int): View {
        val statusBarView = View(activity)
        statusBarView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.setBackgroundColor(color)
        return statusBarView
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

}