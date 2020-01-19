package com.example.textoutput_190120.presentation.activities.main

import com.example.textoutput_190120.presentation.fragments.search.SearchModel


interface MainListener {
    fun onRepositoryClicked(url: String)
    fun onSearchClicked(searchModel: SearchModel)
    fun openRepositoriesFragment(query: String)
}