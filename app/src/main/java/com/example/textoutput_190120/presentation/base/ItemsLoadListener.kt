package com.example.textoutput_190120.presentation.base

interface ItemsLoadListener<T> {

    fun onItemsLoaded(item: T?)

    fun displayProgress()

    fun onLoadError(errorMessage: String)
}