package com.bravo.invoice.di

import android.content.Context
import androidx.room.Room
import com.bravo.data.Database
import com.bravo.data.database.dao.ClientDao
import com.bravo.invoice.InvoiceMakerApplication
import com.bravo.invoice.common.Preferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(): Context = InvoiceMakerApplication.app
    @Provides
    @Singleton
    fun providesPreferences(context : Context) : Preferences{
        val sharedPreferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val rxSharedPreferences = RxSharedPreferences.create(sharedPreferences)
        return Preferences(rxSharedPreferences)
    }
    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database = Room.databaseBuilder(
        context,
        Database::class.java,
        Database.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideStyleDao(database: Database): ClientDao = database.clientsDao()



}