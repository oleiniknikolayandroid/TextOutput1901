package com.example.textoutput_190120.domain

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.textoutput_190120.data.card_models.RepositoryDisplayModel
import com.example.textoutput_190120.presentation.base.ItemsLoadListener
import com.example.textoutput_190120.repository.data_source.database.entity.RepositoryEntity
import com.example.textoutput_190120.utils.CONTENT_PAGE_SIZE
import com.example.textoutput_190120.utils.DEFAULT_INITIAL_LOADED_KEY



abstract class BasePagingViewModel : BaseViewModel() {

    protected lateinit var listCards: LiveData<PagedList<RepositoryDisplayModel>>
    protected lateinit var itemLoadedListener: ItemsLoadListener<PagedList<RepositoryDisplayModel>>

    private val refreshing=ObservableBoolean()
    private val loading=ObservableBoolean()

    private lateinit var pagedListConfiguration: PagedList.Config
    private var currentPage: Int = 1

    fun initPagedConfig() {
        pagedListConfiguration=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(CONTENT_PAGE_SIZE)
            .setPrefetchDistance(CONTENT_PAGE_SIZE / 2)
            .build()
    }

    fun initPagedListLiveData(factory: DataSource.Factory<Int, RepositoryDisplayModel>) {
        listCards=initPagedList(factory, DEFAULT_INITIAL_LOADED_KEY)
    }

    private fun initPagedList(
        factory: DataSource.Factory<Int, RepositoryDisplayModel>,
        initialLoadKey: Int
    ): LiveData<PagedList<RepositoryDisplayModel>> {
        setLoading(false)
        return LivePagedListBuilder(factory, pagedListConfiguration)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<RepositoryDisplayModel>() {
                override fun onItemAtEndLoaded(itemAtEnd: RepositoryDisplayModel) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    currentPage.let { page ->
                        if (itemAtEnd.getBaseModel() is RepositoryEntity)
                            rangeData(itemAtEnd.getBaseModel().username, page)
                        currentPage++
                    }
                }
            })
            .setInitialLoadKey(initialLoadKey)
            .build()
    }

    fun getRefreshing(): ObservableBoolean {
        return refreshing
    }

    fun setRefreshing(isRefreshing: Boolean) {
        refreshing.set(isRefreshing)
    }

    fun isLoading(): ObservableBoolean {
        return loading
    }

    protected fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }

    abstract fun fetchData(username: String)

    abstract fun rangeData(username: String, page: Int)

    abstract fun clearCachedItems()
}