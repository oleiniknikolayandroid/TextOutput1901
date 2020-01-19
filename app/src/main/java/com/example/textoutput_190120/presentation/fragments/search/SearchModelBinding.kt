package com.example.textoutput_190120.presentation.fragments.search

import com.example.textoutput_190120.presentation.activities.main.MainListener


class SearchModelBinding(
    private val searchModel: SearchModel,
    private val listener: MainListener
) {
    fun onSearchClick() {
        listener.onSearchClicked(searchModel)
    }
}