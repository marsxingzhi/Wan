package com.abyte.wan.ext

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.abyte.core.ext.log
import com.abyte.core.ext.otherwise
import com.abyte.core.ext.yes
import com.abyte.wan.main.nav.NavigationItem
import com.google.android.material.navigation.NavigationView


// crossinline
inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        block()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                removeOnLayoutChangeListener(this)
                block()
            }
        })
    }
}


@SuppressLint("RestrictedApi")
fun NavigationView.selectItem(@IdRes resId: Int) {
    doOnLayoutAvailable {
        log("select Item: ${NavigationItem[resId].title}")
        setCheckedItem(resId)
        (menu.findItem(resId) as MenuItemImpl)()
    }
}

inline fun DrawerLayout.afterClosed(crossinline block: () -> Unit) {
    isDrawerOpen(GravityCompat.START).yes {
        closeDrawer(GravityCompat.START)
        addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) = Unit
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
            override fun onDrawerOpened(drawerView: View) = Unit

            override fun onDrawerClosed(drawerView: View) {
                removeDrawerListener(this)
                block()
            }
        })
    }.otherwise {
        block()
    }
}