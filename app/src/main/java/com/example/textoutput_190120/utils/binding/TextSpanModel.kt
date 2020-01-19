package com.example.textoutput_190120.utils.binding

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import com.example.textoutput_190120.R


class TextSpanModel(

    var spanText: String,

    var text: String,

    spanPosition: SpanTextPosition? = SpanTextPosition.FRONT,

    var spanTextSizeRes: Int,

    var textSizeRes: Int,

    var spanTextColorRes: Int,

    var textColorRes: Int,

    var typeface: Int?=Typeface.NORMAL
) {


    enum class SpanTextPosition {
        FRONT,

        LAST,
    }

    private val bodyText: String=
        spanPosition.takeIf { it == SpanTextPosition.FRONT }?.let { "$spanText $text" } ?: "$text $spanText"

    private val startSpanText: Int=
        spanPosition.takeIf { it == SpanTextPosition.FRONT }?.let { 0 } ?: text.length + 1

    private val endSpanText: Int=
        spanPosition.takeIf { it == SpanTextPosition.FRONT }?.let { spanText.length + 1 } ?: bodyText.length

    private val startText: Int=
        spanPosition.takeIf { it == SpanTextPosition.FRONT }?.let { spanText.length + 1 } ?: 0

    private val endText: Int=
        spanPosition.takeIf { it == SpanTextPosition.FRONT }?.let { bodyText.length } ?: text.length + 1

    private fun spanTextColor(context: Context): Int=
        spanTextColorRes.takeIf { it != -1 }?.let { ContextCompat.getColor(context, spanTextColorRes); }
            ?: ContextCompat.getColor(context, R.color.colorBlack)

    private fun textColor(context: Context): Int=
        textColorRes.takeIf { it != -1 }?.let { ContextCompat.getColor(context, textColorRes); }
            ?: ContextCompat.getColor(context, R.color.colorBlack)

    private fun differentSize(context: Context): Float {
        return if (spanTextSizeRes != -1 && textSizeRes != -1)
            context.resources.getDimension(textSizeRes) /
                    context.resources.getDimension(spanTextSizeRes)
        else 1.0f
    }

    fun prepareContent(context: Context): Spannable {
        val span=SpannableString(bodyText)

        val different: Float=differentSize(context)

        span.setSpan(
            RelativeSizeSpan(different),
            startText,
            endText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            RelativeSizeSpan(1f),
            startSpanText,
            endSpanText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            ForegroundColorSpan(textColor(context)),
            startText,
            endText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            ForegroundColorSpan(spanTextColor(context)),
            startSpanText,
            endSpanText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            typeface?.let { StyleSpan(it) },
            startSpanText,
            endSpanText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }


    fun prepareContentWithList(context: Context): Spannable {
        val span=SpannableString(bodyText)

        val different: Float=differentSize(context)

        span.setSpan(
            RelativeSizeSpan(different),
            startText,
            endText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            RelativeSizeSpan(1f),
            startSpanText,
            endSpanText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            ForegroundColorSpan(textColor(context)),
            startText,
            endText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            ForegroundColorSpan(spanTextColor(context)),
            startSpanText,
            endSpanText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        span.setSpan(
            typeface?.let { StyleSpan(it) },
            startSpanText,
            endSpanText,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }

}