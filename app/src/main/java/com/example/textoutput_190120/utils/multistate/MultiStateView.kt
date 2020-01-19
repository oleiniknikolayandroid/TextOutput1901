package com.example.textoutput_190120.utils.multistate

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.example.textoutput_190120.R


annotation class ViewState
class MultiStateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mInflater: LayoutInflater?=null

    private var mContentView: View?=null

    private var mLoadingView: View?=null

    private var mErrorView: View?=null

    private var mEmptyView: View?=null

    private var mAnimateViewChanges=false

    @ViewState
    private var mViewState=
        VIEW_STATE_UNKNOWN

    var viewState: Int
        @ViewState
        get()=mViewState
        set(@ViewState state) {
            if (state != mViewState) {
                val previous=mViewState
                mViewState=state
                setView(previous)
            }
        }

    init {
        mInflater=LayoutInflater.from(context)
        val a=context.obtainStyledAttributes(
            attrs,
            R.styleable.MultiStateView
        )

        val loadingViewResId=a.getResourceId(
            R.styleable.MultiStateView_msv_loadingView, -1
        )
        if (loadingViewResId > -1) {
            mInflater?.let {
                mLoadingView=it.inflate(loadingViewResId, this, false)
                mLoadingView?.apply {
                    addView(this, this.layoutParams)
                }
            }
        }

        val emptyViewResId=a.getResourceId(
            R.styleable.MultiStateView_msv_emptyView, -1
        )
        if (emptyViewResId > -1) {
            mInflater?.let {
                mEmptyView=it.inflate(emptyViewResId, this, false)
                mEmptyView?.apply {
                    addView(this, this.layoutParams)
                }
            }
        }

        val errorViewResId=a.getResourceId(
            R.styleable.MultiStateView_msv_errorView, -1
        )
        if (errorViewResId > -1) {
            mInflater?.let {
                mErrorView=it.inflate(emptyViewResId, this, false)
                mErrorView?.apply {
                    addView(this, this.layoutParams)
                }
            }
        }

        val viewState=a.getInt(
            R.styleable.MultiStateView_msv_viewState,
            VIEW_STATE_CONTENT
        )
        mAnimateViewChanges=a.getBoolean(
            R.styleable.MultiStateView_msv_animateViewChanges, false
        )

        when (viewState) {
            VIEW_STATE_CONTENT -> mViewState=
                VIEW_STATE_CONTENT

            VIEW_STATE_ERROR -> mViewState=
                VIEW_STATE_ERROR

            VIEW_STATE_EMPTY -> mViewState=
                VIEW_STATE_EMPTY

            VIEW_STATE_LOADING -> mViewState=
                VIEW_STATE_LOADING

            VIEW_STATE_UNKNOWN -> mViewState=
                VIEW_STATE_UNKNOWN
            else -> mViewState=
                VIEW_STATE_UNKNOWN
        }

        a.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mContentView == null)
            throw IllegalArgumentException("Content view is not defined")
        setView(VIEW_STATE_UNKNOWN)
    }

    override fun addView(child: View?) {
        if (isValidContentView(child))
            mContentView=child
        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        if (isValidContentView(child))
            mContentView=child
        super.addView(child, index)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child))
            mContentView=child
        super.addView(child, index, params)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child))
            mContentView=child
        super.addView(child, params)
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (isValidContentView(child))
            mContentView=child
        super.addView(child, width, height)
    }

    override fun addViewInLayout(
        child: View, index: Int,
        params: ViewGroup.LayoutParams
    ): Boolean {
        if (isValidContentView(child))
            mContentView=child
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(
        child: View, index: Int,
        params: ViewGroup.LayoutParams, preventRequestLayout: Boolean
    ): Boolean {
        if (isValidContentView(child))
            mContentView=child
        return super
            .addViewInLayout(child, index, params, preventRequestLayout)
    }

    fun getView(@ViewState state: Int): View? {
        when (state) {
            VIEW_STATE_LOADING -> return mLoadingView

            VIEW_STATE_CONTENT -> return mContentView

            VIEW_STATE_EMPTY -> return mEmptyView

            VIEW_STATE_ERROR -> return mErrorView

            else -> return null
        }
    }

    private fun hideLoadingView() {
        mLoadingView?.let {
            it.visibility=View.GONE
            val shimmerLayout=it.findViewById<View>(R.id.shimmerLayout)
            if (shimmerLayout != null) {
                if (shimmerLayout is ShimmerLayout) {
                    (shimmerLayout as ShimmerLayout).stopShimmerAnimation()
                } else {
                    (shimmerLayout as ShimmerFrameLayout).stopShimmerAnimation()
                }
            }
        }
    }

    private fun setView(@ViewState previousState: Int) {
        when (mViewState) {
            VIEW_STATE_LOADING -> {
                if (mLoadingView == null) {
                    throw NullPointerException("Loading View")
                }

                mContentView?.let { it.visibility=View.GONE }
                mErrorView?.let { it.visibility=View.GONE }
                mEmptyView?.let { it.visibility=View.GONE }
                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    mLoadingView?.let { it.visibility=View.VISIBLE }
                }
            }

            VIEW_STATE_EMPTY -> {
                if (mEmptyView == null) {
                    throw NullPointerException("Empty View")
                }

                hideLoadingView()
                mErrorView?.let { it.visibility=View.GONE }
                mContentView?.let { it.visibility=View.GONE }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    mEmptyView?.let { it.visibility=View.VISIBLE }
                }
            }

            VIEW_STATE_ERROR -> {
                if (mErrorView == null) {
                    throw NullPointerException("Error View")
                }

                hideLoadingView()

                mContentView?.let { it.visibility=View.GONE }
                mEmptyView?.let { it.visibility=View.GONE }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    mErrorView?.let { it.visibility=View.GONE }
                }
            }

            VIEW_STATE_CONTENT -> {
                if (mContentView == null) {
                    throw NullPointerException("Content View")
                }

                hideLoadingView()
                mErrorView?.let { it.visibility=View.GONE }
                mEmptyView?.let { it.visibility=View.GONE }
                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    mContentView?.let { it.visibility=View.VISIBLE }
                }
            }
            else -> {
                if (mContentView == null) {
                    throw NullPointerException("Content View")
                }
                hideLoadingView()
                mErrorView?.let { it.visibility=View.GONE }
                mEmptyView?.let { it.visibility=View.GONE }
                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState))
                } else {
                    mContentView?.let { it.visibility=View.VISIBLE }
                }
            }
        }
    }

    private fun isValidContentView(view: View?): Boolean {
        return if (mContentView != null && mContentView !== view) {
            false
        } else view !== mLoadingView && view !== mErrorView && view !== mEmptyView

    }

    @JvmOverloads
    fun setViewForState(
        view: View, @ViewState state: Int,
        switchToState: Boolean=false
    ) {
        when (state) {
            VIEW_STATE_LOADING -> {
                if (mLoadingView != null)
                    removeView(mLoadingView)
                mLoadingView=view
                addView(mLoadingView)
            }

            VIEW_STATE_EMPTY -> {
                if (mEmptyView != null)
                    removeView(mEmptyView)
                mEmptyView=view
                addView(mEmptyView)
            }

            VIEW_STATE_ERROR -> {
                if (mErrorView != null)
                    removeView(mErrorView)
                mErrorView=view
                addView(mErrorView)
            }

            VIEW_STATE_CONTENT -> {
                if (mContentView != null)
                    removeView(mContentView)
                mContentView=view
                addView(mContentView)
            }
        }

        setView(VIEW_STATE_UNKNOWN)
        if (switchToState)
            viewState=state
    }

    @JvmOverloads
    fun setViewForState(
        @LayoutRes layoutRes: Int, @ViewState state: Int,
        switchToState: Boolean=false
    ) {
        if (mInflater == null)
            mInflater=LayoutInflater.from(context)
        mInflater?.let {
            val view=it.inflate(layoutRes, this, false)
            setViewForState(view, state, switchToState)
        }
    }

    fun setAnimateLayoutChanges(animate: Boolean) {
        mAnimateViewChanges=animate
    }

    private fun animateLayoutChange(previousView: View?) {
        if (previousView == null) {
            getView(mViewState)?.let { it.visibility=View.VISIBLE }
            return
        }

        previousView.visibility=View.VISIBLE
        val anim=ObjectAnimator.ofFloat(
            previousView, "alpha",
            1.0f, 0.0f
        ).setDuration(250L)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                previousView.visibility=View.GONE
                getView(mViewState)?.let { it.visibility=View.VISIBLE }
                ObjectAnimator
                    .ofFloat(getView(mViewState), "alpha", 0.0f, 1.0f)
                    .setDuration(250L).start()
            }
        })
        anim.start()
    }

    companion object {

        const val VIEW_STATE_UNKNOWN=-1

        const val VIEW_STATE_CONTENT=0

        const val VIEW_STATE_ERROR=1

        const val VIEW_STATE_EMPTY=2

        const val VIEW_STATE_LOADING=3
    }
}