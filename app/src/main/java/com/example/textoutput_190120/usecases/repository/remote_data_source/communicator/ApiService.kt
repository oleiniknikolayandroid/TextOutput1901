package com.example.textoutput_190120.usecases.repository.remote_data_source.communicator

import com.example.textoutput_190120.repository.data_source.database.entity.RepositoryEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/users/{user}/repos")
    fun fetchRepositories(
        @Path("user") user: String,
        @Query("page") lastPage: Int,
        @Query("per_page") perPage: Int
    ): Single<Response<List<RepositoryEntity>>>
}
