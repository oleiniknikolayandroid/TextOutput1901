package com.example.textoutput_190120.presentation.fragments.repositories

import android.content.Context
import android.graphics.Typeface
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.textoutput_190120.R
import com.example.textoutput_190120.data.card_models.RepositoryDisplayModel
import com.example.textoutput_190120.presentation.activities.main.MainListener
import com.example.textoutput_190120.utils.binding.TextSpanModel


class RepositoryModelBinding(private val data : RepositoryDisplayModel,
                             private val listener : MainListener
)
    : BaseObservable() {

    private var context: Context = listener as Context

    val repositoryName: String
        @Bindable get() {
            return data.getBaseModel().name
        }

    val repositoryId: TextSpanModel
        @Bindable get() {
            return TextSpanModel(
                data.getRepositoryId(),
                context.resources.getString(R.string.format_id),
                TextSpanModel.SpanTextPosition.LAST,
                R.dimen.txt_size_20,
                R.dimen.txt_size_14,
                R.color.colorGreyishBrown,
                R.color.colorBlack,
                Typeface.NORMAL
            )
        }

    val createdAtDate: TextSpanModel
        @Bindable get() {
            return TextSpanModel(
                data.getBaseModel().getParsedCreatedAtDate(),
                context.resources.getString(R.string.format_date),
                TextSpanModel.SpanTextPosition.LAST,
                R.dimen.txt_size_20,
                R.dimen.txt_size_14,
                R.color.colorGreyishBrown,
                R.color.colorBlack,
                Typeface.NORMAL
            )
        }

    val createdAtTime: TextSpanModel
        @Bindable get() {
            return TextSpanModel(
                data.getBaseModel().getParsedCreatedAtTime(),
                context.resources.getString(R.string.format_time),
                TextSpanModel.SpanTextPosition.LAST,
                R.dimen.txt_size_20,
                R.dimen.txt_size_14,
                R.color.colorGreyishBrown,
                R.color.colorBlack,
                Typeface.NORMAL
            )
        }

    val language: TextSpanModel
        @Bindable get() {
            return TextSpanModel(
                data.getBaseModel().language ?: "",
                context.resources.getString(R.string.format_language),
                TextSpanModel.SpanTextPosition.LAST,
                R.dimen.txt_size_20,
                R.dimen.txt_size_14,
                R.color.colorGreyishBrown,
                R.color.colorBlack,
                Typeface.NORMAL
            )
        }

    val defaultBranch: TextSpanModel
        @Bindable get() {
            return TextSpanModel(
                data.getBaseModel().defaultBranch,
                context.resources.getString(R.string.format_branch),
                TextSpanModel.SpanTextPosition.LAST,
                R.dimen.txt_size_20,
                R.dimen.txt_size_14,
                R.color.colorGreyishBrown,
                R.color.colorBlack,
                Typeface.NORMAL
            )
        }

    val repositoryDescription: String
        @Bindable get() {
            return data.getBaseModel().description ?: ""
        }

    val repositoryUrl: String
        @Bindable get() {
            return data.getBaseModel().htmlUrl
        }

    val commitsUrl: String
        @Bindable get() {
            return data.getBaseModel().getCommitsUrl()
        }

    fun onRepositoryClicked(url: String){
        listener.onRepositoryClicked(url)
    }
}
