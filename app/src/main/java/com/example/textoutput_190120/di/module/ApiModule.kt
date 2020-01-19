package com.example.textoutput_190120.di.module


import com.example.textoutput_190120.BuildConfig
import com.example.textoutput_190120.di.scope.ApiScope
import com.example.textoutput_190120.usecases.repository.remote_data_source.RepositoryRemoteDataSource
import com.example.textoutput_190120.usecases.repository.remote_data_source.RepositoryRemoteDataSourceImpl
import com.example.textoutput_190120.usecases.repository.remote_data_source.communicator.ApiService
import com.example.textoutput_190120.usecases.repository.remote_data_source.communicator.ServerCommunicator
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    companion object {
        private val API_URL = "https://api.github.com/"
    }

    @Provides
    @ApiScope
    fun provideOkHttpClient(): OkHttpClient {
        var httpClient=OkHttpClient.Builder()
            .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            httpClient=httpClient
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level=HttpLoggingInterceptor.Level.BODY
                    }
                )
        }

        return httpClient.build()
    }

    @Provides
    @ApiScope
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @ApiScope
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(API_URL).build()
    }

    @Provides
    @ApiScope
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(
            ApiService::class.java)
    }

    @Provides
    @ApiScope
    fun provideCommunicator(apiService: ApiService): ServerCommunicator {
        return ServerCommunicator(
            apiService
        )
    }

    @Provides
    @ApiScope
    fun providesFeedRemoteDataSource(serverCommunicator: ServerCommunicator): RepositoryRemoteDataSource {
        return RepositoryRemoteDataSourceImpl(serverCommunicator)
    }
}
