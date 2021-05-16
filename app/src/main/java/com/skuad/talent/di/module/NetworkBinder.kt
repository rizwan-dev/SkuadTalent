package com.skuad.talent.di.module

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.google.firebase.auth.FirebaseAuth
import com.skuad.talent.data.api.HeaderInterceptor
import com.skuad.talent.data.repository.DashboardRepoImpl
import com.skuad.talent.domain.repository.SharedPrefRepo
import com.skuad.talent.data.repository.SharedPrefRepoImpl
import com.skuad.talent.domain.repository.DashboardRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkBinder {

    @Singleton
    @Provides
    fun provideSharedPrefRepo(app: Application): SharedPrefRepo =
        SharedPrefRepoImpl(app.applicationContext)

    @Provides
    fun provideHeaderInterceptor(
        sharedPrefRepo: SharedPrefRepoImpl
    ): HeaderInterceptor = HeaderInterceptor(sharedPrefRepo)

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(headerInterceptor)
            .build()
    }


    /*@Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()*/


    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient, application: Application): ApolloClient {
        val sqlCacheFactory = SqlNormalizedCacheFactory(application.applicationContext)
        val memoryFirstThenSqlCacheFactory = LruNormalizedCacheFactory(
            EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build()
        ).chain(sqlCacheFactory)

        return ApolloClient
            .builder()
            .normalizedCache(memoryFirstThenSqlCacheFactory)
            .defaultHttpCachePolicy(HttpCachePolicy.NETWORK_FIRST)
            .defaultResponseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
            .serverUrl("https://gql-dev.skuad.in/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideDashboardRepo(apolloClient: ApolloClient): DashboardRepo = DashboardRepoImpl(apolloClient)
}