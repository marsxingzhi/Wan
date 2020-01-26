package com.abyte.wan.core.config

import com.abyte.wan.AppContext
import com.abyte.wan.R
import com.abyte.wan.account.AccountManager
import com.abyte.wan.ext.pref

object Settings {

    var lastPage: Int
        get() = if (lastPageId.isEmpty()) 0 else AppContext.resources.getIdentifier(
            lastPageId,
            "id",
            AppContext.packageName
        )
        set(value) {
            lastPageId = AppContext.resources.getResourceEntryName(value)
        }

    val defaultPage: Int
        get() = if (AccountManager.isLogin()) defaultPageForUser else defaultPageForVisitor

    private var lastPageId by pref("")

    private var defaultPageForUser by pref(R.id.home)

    private var defaultPageForVisitor by pref(R.id.home)
}