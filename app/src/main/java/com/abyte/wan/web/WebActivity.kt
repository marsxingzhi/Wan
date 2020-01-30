package com.abyte.wan.web

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import com.abyte.wan.R
import com.abyte.wan.core.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*
import org.jetbrains.anko.toast

class WebActivity : BaseActivity() {

    private val webViewController by lazy {
        WebViewController(this, webview)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val linkUrl = intent?.getStringExtra(KEY_LINK)
        if (!TextUtils.isEmpty(linkUrl)) {
            webViewController.loadUrl(linkUrl!!)
        } else {
            toast("linkUrl为空")
            finish()
        }
    }

    override fun onDestroy() {
        webview?.apply {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
            (webview.parent as ViewGroup).removeView(webview)
            destroy()
        }
        super.onDestroy()
    }


    companion object {


        private const val KEY_LINK = "key_link"

        fun startWebActivity(activity: BaseActivity, link: String) {
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(KEY_LINK, link)
            activity.startActivity(intent)
        }
    }
}