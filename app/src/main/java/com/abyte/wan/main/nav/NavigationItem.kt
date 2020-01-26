package com.abyte.wan.main.nav

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.abyte.wan.R
import com.abyte.wan.knowledge.KnowledgeFragment
import com.abyte.wan.main.MainFragment
import com.abyte.wan.me.MeFragment
import com.abyte.wan.project.ProjectFragment
import com.abyte.wan.wechat.WechatFragment

class NavigationItem private constructor(
    val groupId: Int = 0,
    val title: String,
    @DrawableRes val iconResId: Int,
    val fragmentClass: Class<out Fragment>,
    val arguments: Bundle? = null
) {


    companion object {
        private val ITEMS = mapOf(
            R.id.home to NavigationItem(
                0,
                "Home",
                R.drawable.ic_launcher_background,
                MainFragment::class.java
            ),
            R.id.knowledge to NavigationItem(
                0,
                "Knowledge",
                R.drawable.ic_launcher_background,
                KnowledgeFragment::class.java
            ),
            R.id.wechat to NavigationItem(
                0,
                "Wechat",
                R.drawable.ic_launcher_background,
                WechatFragment::class.java
            ),
            R.id.project to NavigationItem(
                0,
                "Project",
                R.drawable.ic_launcher_background,
                ProjectFragment::class.java
            ),
            R.id.me to NavigationItem(
                0,
                "Me",
                R.drawable.ic_launcher_background,
                MeFragment::class.java
            )
        )

        operator fun get(@IdRes navId: Int): NavigationItem {
            return ITEMS[navId] ?: ITEMS[R.id.home]!!
        }

        operator fun get(item: NavigationItem): Int {
            return ITEMS.filter { it.value == item }.keys.first()
        }
    }

    override fun toString(): String {
        return "NavigationItem(groupId=$groupId, title=$title, icon=$iconResId, fragmentClass=$fragmentClass)"
    }
}