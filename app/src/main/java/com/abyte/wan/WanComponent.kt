package com.abyte.wan

import android.app.Application
import com.abyte.core.di.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class])
interface WanComponent : AndroidInjector<WanApplication> {

    @Component.Builder
    interface Builder {

        fun build(): WanComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}