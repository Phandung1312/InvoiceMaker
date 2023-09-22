package com.bravo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bravo.data.database.dao.ClientDao
import com.bravo.data.database.dao.ProjectDao
import com.bravo.domain.model.Client
import com.bravo.domain.model.ContactInfoProject
import com.bravo.domain.model.converters.Converters
import com.bravo.domain.model.Project
import com.bravo.domain.model.converters.ContactInfoConverter

@Database(
    entities = [Client::class,Project::class,ContactInfoProject::class],
    version = 1
)
@TypeConverters(Converters::class, ContactInfoConverter::class)
abstract class InvoiceMakerDatabase : RoomDatabase() {
    abstract fun clientsDao(): ClientDao
    abstract fun projectDao() :ProjectDao
    companion object {
        const val DB_NAME = "App_database"
    }
}