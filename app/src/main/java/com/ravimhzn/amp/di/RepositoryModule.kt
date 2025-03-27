package com.ravimhzn.amp.di

import com.ravimhzn.amp.task.repo.AccountDataSource
import com.ravimhzn.amp.task.repo.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAccountDataSource(repository: AccountRepository): AccountDataSource = repository
}
