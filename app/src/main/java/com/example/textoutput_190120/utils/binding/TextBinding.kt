package com.example.textoutput_190120.utils.binding

import android.text.TextUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.textoutput_190120.utils.extention.updateVisibility


class TextBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("textSpan")
        fun bindSpanText(
            textView: TextView,
            textSpanModel: TextSpanModel
        ) {
            if (textSpanModel.text.isNullOrEmpty()) {
                textView.updateVisibility(false)
                return
            }
            textView.setText(textSpanModel.prepareContent(textView.context), TextView.BufferType.SPANNABLE)
        }

        @JvmStatic
        @BindingAdapter("dateSpan", "timeSpan")
        fun bindSpanDate(
            textView: TextView,
            dateSpanModel: TextSpanModel,
            timeSpanModel: TextSpanModel
        ) {
            if (dateSpanModel.text.isNullOrEmpty() || timeSpanModel.text.isNullOrEmpty()) {
                textView.updateVisibility(false)
                return
            }
            textView.text = TextUtils.concat(dateSpanModel.prepareContent(textView.context), " ", timeSpanModel.prepareContent(textView.context))
        }

        @JvmStatic
        @BindingAdapter("bindTextWithCheckContent")
        fun bindTextWithCheckContent(
            textView: TextView,
            text: String
        ) {
            if (text.isNullOrEmpty()){
                textView.updateVisibility(false)
                return
            }
            textView.text = text
        }
    }
}
