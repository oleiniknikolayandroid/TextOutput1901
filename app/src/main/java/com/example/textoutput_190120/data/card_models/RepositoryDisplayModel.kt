package com.example.textoutput_190120.data.card_models

import com.example.textoutput_190120.repository.data_source.database.entity.RepositoryEntity


class RepositoryDisplayModel(private var repositoryEntity : RepositoryEntity) {

    fun getRepositoryId(): String {
        return repositoryEntity.id.toString()
    }

    fun getBaseModel(): RepositoryEntity {
        return repositoryEntity
    }

}