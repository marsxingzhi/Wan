package com.abyte.wan.main.nav

import android.view.MenuItem
import com.abyte.core.ext.log
import com.abyte.user.manager.UserManager
import com.abyte.wan.R
import com.abyte.wan.core.config.Settings
import com.abyte.wan.ext.doOnLayoutAvailable
import com.abyte.wan.ext.loadWithGlide
import com.abyte.wan.ext.selectItem
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.main_nav_header.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.listeners.onClick

class NavigationController(
    private val navigationView: NavigationView,
    private val onNavigationItemChanged: (NavigationItem) -> Unit,
    private val onHeaderClick: () -> Unit,
    private val onRankClick: () -> Unit
) : NavigationView.OnNavigationItemSelectedListener {


    init {
        navigationView.setNavigationItemSelectedListener(this)
    }

    private var currentItem: NavigationItem? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.apply {
            Settings.lastPage = item.itemId
            onNavigationItemChanged(NavigationItem[item.itemId])
        }
        return true
    }

    fun selectProperItem() {
        navigationView.doOnLayoutAvailable {
            val pageItem = currentItem?.let {
                NavigationItem[it]
            } ?: Settings.lastPage
            val needShowPage = pageItem.takeIf {
                navigationView.menu.findItem(it) != null
            } ?: run {
                Settings.defaultPage
            }
            needShowPage.let(navigationView::selectItem)

            navigationView.navHeader.onClick {
                onHeaderClick()
            }

            navigationView.containerRankIcon.onClick {
                onRankClick()
            }
        }
    }

    private fun updateHeaderView() {
        navigationView.doOnLayoutAvailable {
            if (UserManager.currentUser == null) {
                navigationView.userAvatar.imageResource = R.drawable.ic_github
                navigationView.userName.text = "请登录"
                navigationView.userEmail.text = "xx@kotliner.cn"
            } else {
                navigationView.userName.text =
                    UserManager.currentUser!!.nickname.takeIf { nickname -> nickname.isNotEmpty() }
                        ?: "行知"
                navigationView.userEmail.text =
                    UserManager.currentUser!!.email.takeIf { email -> email.isNotEmpty() }
                        ?: "geyan0117@gmail.com"
                navigationView.userAvatar.loadWithGlide(
                    UserManager.currentUser!!.icon,
                    UserManager.currentUser!!.nickname.first()
                )
            }
        }
    }

    fun updateView() {
        log("NavigationController---updateView")
        updateHeaderView()
    }

    fun updateNoLoginView() {
        log("NavigationController---updateNoLoginView")
        updateHeaderView()
    }
}