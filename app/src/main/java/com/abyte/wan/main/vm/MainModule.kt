package com.abyte.wan.main.vm

import androidx.lifecycle.ViewModel
import com.abyte.core.di.ViewModelKey
import com.abyte.core.services.RETROFIT
import com.abyte.wan.main.api.MainApi
import com.abyte.wan.main.repo.MainRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MainModule {

    @Provides
    fun provideMainApi(): MainApi {
        return RETROFIT.create(MainApi::class.java)
    }

    @Provides
    fun provideMainRepository(api: MainApi): MainRepository {
        return MainRepository(api)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(repo: MainRepository): ViewModel {
        return MainViewModel(repo)
    }
}