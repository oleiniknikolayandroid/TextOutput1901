package com.example.textoutput_190120.usecases.repository.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.textoutput_190120.repository.data_source.database.entity.RepositoryEntity
import com.example.textoutput_190120.usecases.repository.data_source.database.dao.RepositoryDao

@Database(entities = [RepositoryEntity::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}