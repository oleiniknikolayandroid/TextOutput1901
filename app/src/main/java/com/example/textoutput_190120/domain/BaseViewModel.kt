package com.example.textoutput_190120.domain

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.textoutput_190120.presentation.base.LoadingState
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable=CompositeDisposable()
    val macroLoadingState = MediatorLiveData<LoadingState>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}