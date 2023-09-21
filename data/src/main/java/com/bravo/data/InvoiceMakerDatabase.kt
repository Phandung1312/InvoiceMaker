package com.bravo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bravo.data.database.dao.ClientDao
import com.bravo.data.database.dao.ProjectDao
import com.bravo.domain.model.Client
import com.bravo.domain.model.converters.Converters
import com.bravo.domain.model.Project

@Database(
    entities = [Client::class,Project::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class InvoiceMakerDatabase : RoomDatabase() {
    abstract fun clientsDao(): ClientDao
    abstract fun projectDao() :ProjectDao
    companion object {
        const val DB_NAME = "App_database"
    }
}