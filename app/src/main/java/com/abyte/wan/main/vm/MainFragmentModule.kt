package com.abyte.wan.main.vm

import com.abyte.core.di.PerFragment
import com.abyte.wan.knowledge.KnowledgeFragment
import com.abyte.wan.knowledge.module.KnowledgeFragmentModule
import com.abyte.wan.knowledge.module.KnowledgeModule
import com.abyte.wan.main.MainFragment
import com.abyte.wan.me.MeFragment
import com.abyte.wan.project.ProjectFragment
import com.abyte.wan.project.module.ProjectFragmentModule
import com.abyte.wan.project.module.ProjectModule
import com.abyte.wan.wechat.WechatFragment
import com.abyte.wan.wechat.module.WechatFragmentModule
import com.abyte.wan.wechat.module.WechatModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainFragment(): MainFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [KnowledgeFragmentModule::class, KnowledgeModule::class])
    abstract fun contributeKnowledgeFragment(): KnowledgeFragment


    @PerFragment
    @ContributesAndroidInjector(modules = [WechatFragmentModule::class, WechatModule::class])
    abstract fun contributeWechatFragment(): WechatFragment


    @PerFragment
    @ContributesAndroidInjector(modules = [ProjectFragmentModule::class, ProjectModule::class])
    abstract fun contributeProjectFragment(): ProjectFragment


    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeMeFragment(): MeFragment
}