package com.example.textoutput_190120.di.component

import com.example.textoutput_190120.di.module.InteractorModule
import com.example.textoutput_190120.di.scope.InteractorScope
import com.example.textoutput_190120.usecases.RepositoriesUseCases

import dagger.Component

@InteractorScope
@Component(modules = [InteractorModule::class], dependencies = [RepositoryComponent::class])
interface InteractorComponent {
    val repositoriesUseCases : RepositoriesUseCases
}