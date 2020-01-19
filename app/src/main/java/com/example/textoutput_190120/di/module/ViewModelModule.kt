package com.example.textoutput_190120.di.module

import android.app.Application
import com.example.textoutput_190120.App
import com.example.textoutput_190120.di.scope.ViewModelScope
import com.example.textoutput_190120.domain.RepositoriesViewModel
import com.example.textoutput_190120.usecases.RepositoriesUseCases

import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(app: App) {

    internal var app: Application = app

    @ViewModelScope
    @Provides
    internal fun providesFeedViewModel(repositoriesUseCases: RepositoriesUseCases): RepositoriesViewModel {
        return RepositoriesViewModel(repositoriesUseCases)
    }
}