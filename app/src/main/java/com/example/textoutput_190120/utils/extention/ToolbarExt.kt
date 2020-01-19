package com.example.textoutput_190120.utils.extention

import android.app.ActionBar
import android.app.Activity
import android.widget.Toolbar

fun Toolbar.initializeToolbar(actionBar : ActionBar, activity : Activity) : Toolbar {
    this.apply {
        setNavigationOnClickListener { activity.onBackPressed() }
        activity.setActionBar(this)
        actionBar.title = ""
    }
    return this
}