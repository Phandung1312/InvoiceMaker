package com.bravo.data.di

import android.content.Context
import androidx.room.Room
import com.bravo.data.InvoiceMakerDatabase
import com.bravo.data.database.dao.ClientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InvoiceMakerDatabase = Room.databaseBuilder(
        context,
        InvoiceMakerDatabase::class.java,
        InvoiceMakerDatabase.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideStyleDao(invoiceMakerDatabase: InvoiceMakerDatabase): ClientDao = invoiceMakerDatabase.clientsDao()

}