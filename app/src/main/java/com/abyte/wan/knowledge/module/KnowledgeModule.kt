package com.abyte.wan.knowledge.module

import androidx.lifecycle.ViewModel
import com.abyte.core.di.ViewModelKey
import com.abyte.core.services.retrofit
import com.abyte.wan.knowledge.api.KnowledgeApi
import com.abyte.wan.knowledge.repo.KnowledgeRepository
import com.abyte.wan.knowledge.vm.KnowledgeViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class KnowledgeModule {


    @Provides
    fun provideKnowledgeApi(): KnowledgeApi {
        return retrofit.create(KnowledgeApi::class.java)
    }

    @Provides
    fun provideJnowledgeRepository(api: KnowledgeApi): KnowledgeRepository {
        return KnowledgeRepository(api)
    }

    @Provides
    @IntoMap
    @ViewModelKey(KnowledgeViewModel::class)
    fun provideKnowledgeViewModel(repo: KnowledgeRepository): ViewModel {
        return KnowledgeViewModel(repo)
    }
}