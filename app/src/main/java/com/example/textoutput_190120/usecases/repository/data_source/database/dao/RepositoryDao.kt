package com.example.textoutput_190120.usecases.repository.data_source.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.example.textoutput_190120.repository.data_source.database.entity.RepositoryEntity

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories")
    fun queryFeeds(): List<RepositoryEntity>

    @Query("SELECT * FROM repositories WHERE id = :id")
    fun queryById(id: Int): RepositoryEntity

    @Insert
    fun insertList(listEntities: List<RepositoryEntity>)

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insert(listEntities: List<RepositoryEntity>)

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun update(entity: RepositoryEntity)

    @Update
    fun updateAll(listEntities: List<RepositoryEntity>)

    @Delete
    fun delete(entity: RepositoryEntity)

    @Query("DELETE FROM repositories WHERE cached = 1")
    fun deleteCachedItems()

    @Query("SELECT * FROM repositories")
    fun getDataSource(): DataSource.Factory<Int, RepositoryEntity>

    @Query("DELETE FROM repositories WHERE cached = 1")
    fun deleteAllCachedItems()

    @Query("DELETE FROM repositories WHERE screenType = :screenType")
    fun deleteAllItemsByType(screenType: String)

    @Query("DELETE FROM repositories WHERE username = :username")
    fun deleteAllItemsByUsername(username: String)

    @Transaction
    fun insertAndClearCache(
        listEntities: List<RepositoryEntity>
    ) {
        listEntities.forEach { it.username.let { deleteAllItemsByUsername(it) } }
        insert(listEntities)
    }
}