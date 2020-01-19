package com.example.textoutput_190120.presentation.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.textoutput_190120.App
import com.example.textoutput_190120.data.card_models.RepositoryDisplayModel
import com.example.textoutput_190120.di.component.ViewModelComponent
import com.example.textoutput_190120.presentation.adapter.paginglist.DiffCallbackBaseCardModel
import com.example.textoutput_190120.presentation.adapter.paginglist.PagingAdapter
import com.example.textoutput_190120.utils.multistate.MultiStateView


abstract class BasePagingFragment<V : ViewDataBinding>: BaseFragment<V>(), ItemsLoadListener<PagedList<RepositoryDisplayModel>> {

    protected var pagingAdapter: PagingAdapter = PagingAdapter(DiffCallbackBaseCardModel())

    abstract fun injectDependency(component: ViewModelComponent)

    abstract fun initListView()

    abstract fun getListView(): RecyclerView

    abstract fun getRefreshView(): SwipeRefreshLayout

    abstract fun getStateView() : MultiStateView

    abstract fun initObserver()

    abstract fun removeObserver()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        displayProgress()
        initListView()
        initObserver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDaggerDependencies()
    }

    override fun onDestroyView() {
        removeObserver()
        super.onDestroyView()
    }

    private fun createDaggerDependencies() {
       activity?.apply { injectDependency((application as App).getViewModelComponent()) }
    }
}