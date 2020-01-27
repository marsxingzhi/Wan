package com.abyte.wan.project.module

import androidx.lifecycle.ViewModel
import com.abyte.core.di.ViewModelKey
import com.abyte.core.services.retrofit
import com.abyte.wan.project.api.ProjectApi
import com.abyte.wan.project.repo.ProjectRepository
import com.abyte.wan.project.vm.ProjectViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ProjectModule {

    @Provides
    fun provideProjectApi(): ProjectApi {
        return retrofit.create(ProjectApi::class.java)
    }

    @Provides
    fun provideProjectRepository(api: ProjectApi): ProjectRepository {
        return ProjectRepository(api)
    }

    @Provides
    @IntoMap
    @ViewModelKey(ProjectViewModel::class)
    fun provideProjectViewModel(repo: ProjectRepository): ViewModel {
        return ProjectViewModel(repo)
    }
}