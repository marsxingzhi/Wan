package com.abyte.wan.rank.module

import androidx.lifecycle.ViewModel
import com.abyte.core.di.ViewModelKey
import com.abyte.core.services.retrofit
import com.abyte.wan.rank.api.RankApi
import com.abyte.wan.rank.repo.RankRepository
import com.abyte.wan.rank.vm.RankViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class RankModule {

    @Provides
    fun provideRankApi(): RankApi {
        return retrofit.create(RankApi::class.java)
    }

    @Provides
    fun provideRankRepository(api: RankApi): RankRepository {
        return RankRepository(api)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RankViewModel::class)
    fun provideRankViewModel(repository: RankRepository): ViewModel {
        return RankViewModel(repository)
    }
}