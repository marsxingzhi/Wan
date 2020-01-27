package com.abyte.wan.knowledge.module

import com.abyte.wan.knowledge.KnowledgeNavigationFragment
import com.abyte.wan.knowledge.SystemTreeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class KnowledgeFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSystemTreeFragment(): SystemTreeFragment


    @ContributesAndroidInjector
    abstract fun contributeKnowledgeNavigationFragment(): KnowledgeNavigationFragment
}