package com.example.textoutput_190120.di.component


import com.example.textoutput_190120.di.module.DatabaseModule
import com.example.textoutput_190120.usecases.repository.data_source.RepositoryDataSource
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val repositoryDataSource: RepositoryDataSource
}
