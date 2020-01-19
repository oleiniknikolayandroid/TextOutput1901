package com.example.textoutput_190120.di.component

import com.example.textoutput_190120.di.module.RepositoryModule
import com.example.textoutput_190120.di.scope.RepositoryScope
import com.example.textoutput_190120.usecases.repository.RepositoriesRepository
import dagger.Component

@RepositoryScope
@Component(modules = [RepositoryModule::class], dependencies = [ApiComponent::class, DatabaseComponent::class])
interface RepositoryComponent {
    val repositoriesRepository: RepositoriesRepository
}