package com.skuad.talent.di.module

import android.app.Application
import com.skuad.talent.data.repository.SharedPrefRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseBinder {
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPrefRepoImpl = SharedPrefRepoImpl(app.applicationContext)
}