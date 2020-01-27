package com.abyte.wan.me

import android.os.Bundle
import android.view.View
import com.abyte.wan.R
import com.abyte.wan.core.base.ui.BaseFragment
import com.abyte.wan.main.MainActivity

class MeFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionBarController.setupWithViewPager(null)
    }

    override fun getLayoutId(): Int = R.layout.main_fragment_me
}