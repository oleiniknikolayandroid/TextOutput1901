package com.example.textoutput_190120.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nshmura.snappysmoothscroller.LinearLayoutScrollVectorDetector
import com.nshmura.snappysmoothscroller.SnappySmoothScroller

class RepositoriesLayoutManager(context: Context) : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
        val scroller=SnappySmoothScroller.Builder()
            .setPosition(position)
            .setScrollVectorDetector(LinearLayoutScrollVectorDetector(this))
            .build(recyclerView.context)

        startSmoothScroll(scroller)
    }

    override fun onFocusSearchFailed(
        focused: View, focusDirection: Int,
        recycler: RecyclerView.Recycler, state: RecyclerView.State
    ): View {
        return focused as? EditText ?: super.onFocusSearchFailed(focused, focusDirection, recycler, state)!!
    }
}
