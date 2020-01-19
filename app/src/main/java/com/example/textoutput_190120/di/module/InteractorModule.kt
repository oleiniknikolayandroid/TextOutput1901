package com.example.textoutput_190120.di.module

import com.example.textoutput_190120.di.scope.InteractorScope
import com.example.textoutput_190120.usecases.RepositoriesUseCases
import com.example.textoutput_190120.usecases.RepositoriesUseCasesImpl
import com.example.textoutput_190120.usecases.repository.RepositoriesRepository
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @InteractorScope
    @Provides
    internal fun providesRepositoriesUseCases(repository: RepositoriesRepository): RepositoriesUseCases {
        return RepositoriesUseCasesImpl(repository)
    }
}