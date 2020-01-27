package com.abyte.wan.login.vm

import androidx.lifecycle.ViewModel
import com.abyte.core.di.ViewModelKey
import com.abyte.core.services.retrofit
import com.abyte.wan.login.api.LoginApi
import com.abyte.wan.login.repo.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class LoginModule {

    @Provides
    fun provideAuthApi(): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    fun provideLoginRepository(loginApi: LoginApi): LoginRepository {
        return LoginRepository(loginApi)
    }

    @Provides
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun provideLoginViewModel(repo: LoginRepository): ViewModel {
        return LoginViewModel(repo)
    }

}