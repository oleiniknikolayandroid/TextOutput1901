package com.example.textoutput_190120.di.module


import com.example.textoutput_190120.di.scope.RepositoryScope
import com.example.textoutput_190120.usecases.repository.RepositoriesRepository
import com.example.textoutput_190120.usecases.repository.RepositoriesRepositoryImpl
import com.example.textoutput_190120.usecases.repository.data_source.RepositoryDataSource
import com.example.textoutput_190120.usecases.repository.remote_data_source.RepositoryRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @RepositoryScope
    @Provides
    internal fun providesFeedRepository(repositoryRemoteDataSource: RepositoryRemoteDataSource, repositoryDataSource: RepositoryDataSource): RepositoriesRepository {
        return RepositoriesRepositoryImpl(repositoryRemoteDataSource, repositoryDataSource)
    }
}