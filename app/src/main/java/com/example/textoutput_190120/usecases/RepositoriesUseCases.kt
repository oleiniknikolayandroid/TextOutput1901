package com.example.textoutput_190120.usecases

import androidx.paging.DataSource
import com.example.textoutput_190120.data.card_models.RepositoryDisplayModel
import com.example.textoutput_190120.usecases.repository.RepositoriesRepository
import com.example.textoutput_190120.utils.ConverterFactory
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RepositoriesUseCases {

    fun fetchRepositories(username: String): Completable

    fun fetchRepositoriesNext(username: String, lastItemId: Int): Completable

    fun deleteCachedFeed(): Completable

    fun getCardsFactory(
        modelConverter: ConverterFactory
    ): DataSource.Factory<Int, RepositoryDisplayModel>
}

class RepositoriesUseCasesImpl(private val repository: RepositoriesRepository) :
    RepositoriesUseCases {

    override fun fetchRepositories(username: String): Completable =
        repository.fetchRepositories(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun fetchRepositoriesNext(username: String, lastItemId: Int): Completable =
        repository.fetchRepositoriesNext(username, lastItemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun deleteCachedFeed(): Completable =
        repository.deleteCachedRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    override fun getCardsFactory(
        modelConverter: ConverterFactory
    ): DataSource.Factory<Int, RepositoryDisplayModel> =
        repository.getFactory(modelConverter)
}