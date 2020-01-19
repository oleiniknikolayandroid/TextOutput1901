package com.example.textoutput_190120.presentation.base

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.textoutput_190120.presentation.activities.main.MainListener
import com.example.textoutput_190120.utils.extention.hideKeyboard
import com.example.textoutput_190120.utils.extention.showSnack
import com.example.textoutput_190120.utils.extention.showToast


abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected lateinit var viewBinder: V
    protected var listener : MainListener? = null
    private val appBar: ActionBar? = activity?.actionBar

    abstract fun getLayoutId(): Int

    abstract fun setupViewLogic(binder : V)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragmentManager?.popBackStackImmediate()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinder = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        setupViewLogic(this.viewBinder)
        return viewBinder.root
    }

    open fun reset() {}

    protected fun displayHomeAsUp() = appBar?.setDisplayHomeAsUpEnabled(false)

    protected fun initializeNavigationBar(title: String, showBackButton: Boolean, @DrawableRes resId: Int) {
        appBar?.apply {
            this.setDisplayHomeAsUpEnabled(showBackButton)
            this.setHomeAsUpIndicator(resId)
            this.elevation = 4f
        }
    }

    protected fun showToast(text: String) = activity?.showToast(text)

    protected fun showSnack(text: String) = activity?.showSnack(text)

    protected fun hideKeyboard() = activity?.hideKeyboard()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement MainListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}