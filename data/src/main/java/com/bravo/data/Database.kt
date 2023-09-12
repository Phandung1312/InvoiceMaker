package com.bravo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bravo.data.database.dao.ClientDao

@Database(
    entities = [ClientDao::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun clientsDao(): ClientDao
    companion object {
        const val DB_NAME = "App_database"
    }
}