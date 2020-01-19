package com.example.textoutput_190120.presentation.activities.main

import android.view.MenuItem
import com.example.textoutput_190120.R
import com.example.textoutput_190120.databinding.MainDataBinding
import com.example.textoutput_190120.di.component.ViewModelComponent
import com.example.textoutput_190120.presentation.base.BaseActivity
import com.example.textoutput_190120.presentation.fragments.repositories.RepositoriesFragment
import com.example.textoutput_190120.presentation.fragments.search.SearchFragment
import com.example.textoutput_190120.presentation.fragments.search.SearchModel
import com.example.textoutput_190120.utils.extention.openWebViewPage
import com.example.textoutput_190120.utils.extention.replaceFragment


const val EXTRAS_USERNAME = "EXTRAS_USERNAME"

class MainActivity : BaseActivity<MainDataBinding>(),
    MainListener {

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupViewLogic(binding: MainDataBinding) {
        openSearchFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRepositoryClicked(url: String) {
        openWebViewPage(url)
    }

    override fun onSearchClicked(searchModel: SearchModel) {
        openRepositoriesFragment(searchModel.query)
    }

    override fun openRepositoriesFragment(query : String) {
        this.replaceFragment(R.id.mainContent, RepositoriesFragment.newInstance(query), true, false)
    }

    private fun openSearchFragment() {
        this.replaceFragment(R.id.mainContent, SearchFragment.newInstance(), false, false)
    }
}
