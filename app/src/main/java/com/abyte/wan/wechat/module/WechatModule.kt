package com.abyte.wan.wechat.module

import androidx.lifecycle.ViewModel
import com.abyte.core.di.ViewModelKey
import com.abyte.core.services.retrofit
import com.abyte.wan.wechat.api.WechatApi
import com.abyte.wan.wechat.repo.WechatRepository
import com.abyte.wan.wechat.vm.WechatViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class WechatModule {

    @Provides
    fun provideWechatApi(): WechatApi {
        return retrofit.create(WechatApi::class.java)
    }

    @Provides
    fun provideWechatRepository(api: WechatApi): WechatRepository {
        return WechatRepository(api)
    }

    @Provides
    @IntoMap
    @ViewModelKey(WechatViewModel::class)
    fun prorvideWechatViewModel(repo: WechatRepository): ViewModel {
        return WechatViewModel(repo)
    }
}