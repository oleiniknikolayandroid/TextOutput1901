package com.example.textoutput_190120.repository.data_source.database.entity

import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.textoutput_190120.data.BaseModel
import com.example.textoutput_190120.utils.CACHED_VALUE
import com.example.textoutput_190120.utils.DEFAULT_CACHE_VALUE
import com.example.textoutput_190120.utils.DEFAULT_SCREEN
import com.example.textoutput_190120.utils.extention.parseDate
import com.example.textoutput_190120.utils.extention.parseTime
import com.google.gson.annotations.SerializedName


const val URL_SUFFIX_COMMITS = "/commits"

@Entity(tableName="repositories")
data class RepositoryEntity(

    @PrimaryKey(autoGenerate=false)
    @SerializedName("id")
    var id: Int,

    @SerializedName("node_id")
    var nodeId: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("description")
    var description: String?,

    @SerializedName("language")
    var language: String?,

    @SerializedName("private")
    var private: Boolean,

    @SerializedName("html_url")
    var htmlUrl: String,

    @SerializedName("tags_url")
    var tagsUrl: String,

    @SerializedName("created_at")
    @Bindable
    var createdAt: String,

    @SerializedName("updated_at")
    var updatedAt: String,

    @SerializedName("pushed_at")
    var pushedAt: String,

    @SerializedName("forks")
    var forks: Int,

    @SerializedName("default_branch")
    var defaultBranch: String,

    var screenType: String?=DEFAULT_SCREEN,

    var username: String,

    var cached: Int?=DEFAULT_CACHE_VALUE
) : BaseModel() {

    override fun convertItemForDataSource(): RepositoryEntity {
        return RepositoryEntity(
            id,
            nodeId,
            name,
            description,
            language,
            private,
            htmlUrl,
            tagsUrl,
            createdAt,
            updatedAt,
            pushedAt,
            forks,
            defaultBranch,
            screenType,
            username,
            cached
        )
    }

    constructor() : this(
        0,
        "",
        "",
        "",
        "",
        false,
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        "",
        "",
        DEFAULT_CACHE_VALUE
    )

    val isRepository: Boolean
        get()=true

    fun getRepoId(): String=id.toString()

    fun getParsedCreatedAtDate(): String=createdAt.parseDate()

    fun getParsedCreatedAtTime(): String=createdAt.parseTime()

    fun getCommitsUrl(): String="$htmlUrl$URL_SUFFIX_COMMITS"

    fun convertItemForDataSource(item: RepositoryEntity, isCached: Boolean?): RepositoryEntity {
        isCached?.let { item.cached=CACHED_VALUE }
        return item
    }
}