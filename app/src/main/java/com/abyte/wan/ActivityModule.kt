package com.abyte.wan

import com.abyte.core.di.PerActivity
import com.abyte.wan.login.LoginActivity
import com.abyte.wan.login.vm.LoginModule
import com.abyte.wan.main.MainActivity
import com.abyte.wan.main.vm.MainFragmentModule
import com.abyte.wan.rank.RankActivity
import com.abyte.wan.rank.module.RankModule
import com.abyte.wan.web.WebActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {


    @PerActivity
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    /**
     * LoginFragmentModule一定得写，
     * 否则会报错：java.lang.IllegalArgumentException: No injector factory bound for Class
     */
    @PerActivity
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginActivity(): LoginActivity


    @PerActivity
    @ContributesAndroidInjector(modules = [RankModule::class])
    abstract fun contributeRankActivity(): RankActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeWebActivity(): WebActivity
}