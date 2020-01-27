package com.abyte.wan.wechat.module

import com.abyte.wan.wechat.WechatArticleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WechatFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeWechatArticleFragment(): WechatArticleFragment

}