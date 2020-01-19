package com.example.textoutput_190120.utils.extention

import android.content.Context
import android.graphics.Rect
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

fun View?.hideKeyboardWithClearFocus() {
    this?.clearFocus()
    val imm=this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(this?.windowToken, 0)
}

fun View.updateVisibility(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}