package com.example.textoutput_190120.di.module


import com.example.textoutput_190120.usecases.repository.data_source.RepositoryDataSource
import com.example.textoutput_190120.usecases.repository.data_source.RepositoryDataSourceImpl
import com.example.textoutput_190120.usecases.repository.data_source.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val appDatabase: AppDatabase) {
    @Provides
    internal fun providesAppDatabase(): AppDatabase {
        return appDatabase
    }

    @Provides
    internal fun providesFeedDataSource(appDatabase: AppDatabase): RepositoryDataSource {
        return RepositoryDataSourceImpl(appDatabase)
    }
}