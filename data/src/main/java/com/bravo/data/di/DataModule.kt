package com.bravo.data.di

import com.bravo.data.repositories.ItemRepositoryImpl
import com.bravo.domain.repositories.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindItemRepository(itemRepositoryImpl: ItemRepositoryImpl) : ItemRepository
}