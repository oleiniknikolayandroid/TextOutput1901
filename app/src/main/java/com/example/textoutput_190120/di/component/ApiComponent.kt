package com.example.textoutput_190120.di.component

import com.example.textoutput_190120.di.module.ApiModule
import com.example.textoutput_190120.di.scope.ApiScope
import com.example.textoutput_190120.usecases.repository.remote_data_source.RepositoryRemoteDataSource
import dagger.Component

@ApiScope
@Component(modules = [ApiModule::class], dependencies = [AppComponent::class])
interface ApiComponent {
    val repositoryRemoteDataSource: RepositoryRemoteDataSource
}
