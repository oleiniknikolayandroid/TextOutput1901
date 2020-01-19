package com.example.textoutput_190120.presentation.base

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.textoutput_190120.App
import com.example.textoutput_190120.R
import com.example.textoutput_190120.di.component.ViewModelComponent
import com.example.textoutput_190120.presentation.navigator.Navigation
import com.example.textoutput_190120.presentation.navigator.NavigationImpl
import com.example.textoutput_190120.utils.extention.hideKeyboard
import com.example.textoutput_190120.utils.extention.initializeToolbar


abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewBinding: V

    protected lateinit var navigator: Navigation

    open lateinit var messageNecessaryPermissions: String

    private var toolbar: Toolbar?=null

    companion object {
        private const val DEBUG_ENABLED=false
    }

    abstract fun injectDependency(component: ViewModelComponent)

    abstract fun getLayoutId(): Int


    abstract fun setupViewLogic(binder: V)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=DataBindingUtil.setContentView(this, getLayoutId())
        navigator= NavigationImpl(this)
        createDaggerDependencies()
        setupViewLogic(viewBinding)
    }

    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }

    override fun onBackPressed() {
        hideKeyboard()
        supportFragmentManager?.backStackEntryCount?.let {
            if (it == 0) {
                navigator.showExitConfirmDialog { finish() }
                return
            } else {
                super.onBackPressed()
            }
        }
    }

    protected fun addOrReplaceFragment(fragment: Fragment, id: Int, tag: String) {
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            fragmentTransaction.add(id, fragment, tag)
        } else {
            fragmentTransaction.replace(id, fragment, tag)
        }
        fragmentTransaction.commit()
    }

    protected fun setWindowFlag(bits: Int, on: Boolean) {
        val win=window
        val winParams=win.attributes
        if (on) {
            winParams.flags=winParams.flags or bits
        } else {
            winParams.flags=winParams.flags and bits.inv()
        }
        win.attributes=winParams
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun initializeToolbar(toolbar: Toolbar) {
        this.toolbar=toolbar.initializeToolbar(actionBar, this)
    }

    protected fun getToolbar(): Toolbar?=this.toolbar

    open fun isDebugEnabled(): Boolean=DEBUG_ENABLED

    private fun createDaggerDependencies() {
        injectDependency((application as App).getViewModelComponent())
    }
}
