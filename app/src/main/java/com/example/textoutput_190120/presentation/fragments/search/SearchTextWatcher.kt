package com.example.textoutput_190120.presentation.fragments.search

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

open class SearchTextWatcher(
    private val callback: SearchCallback,
    private val searchModel: SearchModel,
    private val editText: EditText
): TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        searchModel.query = editText.text.toString().trim()
        callback.enableSearchButton(searchModel.query.isNotEmpty())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

}