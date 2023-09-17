package com.bravo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bravo.data.database.dao.ClientDao
import com.bravo.domain.model.Client

@Database(
    entities = [Client::class],
    version = 1
)
abstract class InvoiceMakerDatabase : RoomDatabase() {
    abstract fun clientsDao(): ClientDao
    companion object {
        const val DB_NAME = "App_database"
    }
}