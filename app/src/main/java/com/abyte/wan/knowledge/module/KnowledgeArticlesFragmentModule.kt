package com.abyte.wan.knowledge.module

import com.abyte.core.di.PerFragment
import com.abyte.wan.knowledge.KnowledgeArticlesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class KnowledgeArticlesFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [KnowledgeModule::class])
    abstract fun contributeKnowledgeArticlesFragment(): KnowledgeArticlesFragment
}