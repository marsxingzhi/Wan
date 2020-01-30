package com.abyte.wan.web

import android.content.Context
import android.webkit.WebView

class WebViewController(private val context: Context, private val webView: WebView) {

    fun loadUrl(url: String) {
        webView.loadUrl(url)
    }
}