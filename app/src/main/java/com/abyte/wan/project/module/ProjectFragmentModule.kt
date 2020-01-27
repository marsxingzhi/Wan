package com.abyte.wan.project.module

import com.abyte.wan.project.ProjectArticleFragment
import com.abyte.wan.wechat.WechatArticleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProjectFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeProjectArticleFragment(): ProjectArticleFragment

}