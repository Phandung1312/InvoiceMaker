package com.bravo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bravo.data.database.dao.ClientDao
import com.bravo.data.database.dao.ItemDao
import com.bravo.data.database.dao.ProjectDao
import com.bravo.domain.model.Client
import com.bravo.domain.model.ContactInfoProject
import com.bravo.domain.model.Item
import com.bravo.domain.model.converters.Converters
import com.bravo.domain.model.Project
import com.bravo.domain.model.converters.ContactInfoConverter

@Database(
    entities = [Client::class,Project::class,ContactInfoProject::class,Item::class],
    version = 1
)
@TypeConverters(Converters::class, ContactInfoConverter::class)
abstract class InvoiceMakerDatabase : RoomDatabase() {
    abstract fun clientsDao(): ClientDao
    abstract fun projectDao() :ProjectDao
    abstract fun itemDao() : ItemDao
    companion object {
        const val DB_NAME = "App_database"
    }
}