package com.example.textoutput_190120.presentation.adapter.paginglist

import androidx.recyclerview.widget.DiffUtil
import com.example.textoutput_190120.data.card_models.RepositoryDisplayModel


class DiffCallbackBaseCardModel : DiffUtil.ItemCallback<RepositoryDisplayModel>() {

    companion object {
        val CONTENT = Any()
    }

    override fun areItemsTheSame(oldRepository: RepositoryDisplayModel, newRepository: RepositoryDisplayModel): Boolean {
        return oldRepository.getRepositoryId() == newRepository.getRepositoryId()
    }

    override fun areContentsTheSame(oldRepository: RepositoryDisplayModel, newRepository: RepositoryDisplayModel): Boolean {
        return oldRepository.getRepositoryId() == newRepository.getRepositoryId()
    }
}