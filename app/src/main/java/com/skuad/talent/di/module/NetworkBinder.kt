package com.skuad.talent.di.module

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.google.firebase.auth.FirebaseAuth
import com.skuad.talent.data.api.ApiManager
import com.skuad.talent.data.api.BaseUrl
import com.skuad.talent.data.api.HeaderInterceptor
import com.skuad.talent.data.api.LoginApi
import com.skuad.talent.data.repository.CandidateRepoImpl
import com.skuad.talent.data.repository.DashboardRepoImpl
import com.skuad.talent.data.repository.LoginRepoImpl
import com.skuad.talent.domain.repository.SharedPrefRepo
import com.skuad.talent.data.repository.SharedPrefRepoImpl
import com.skuad.talent.domain.repository.CandidateRepo
import com.skuad.talent.domain.repository.DashboardRepo
import com.skuad.talent.domain.repository.LoginRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkBinder {

    @Singleton
    @Provides
    fun provideSharedPrefRepo(app: Application): SharedPrefRepo =
        SharedPrefRepoImpl(app.applicationContext)

    @Provides
    fun provideHeaderInterceptor(
        firebaseAuth: FirebaseAuth,
        sharedPrefRepo: SharedPrefRepoImpl
    ): HeaderInterceptor = HeaderInterceptor(firebaseAuth, sharedPrefRepo)

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

    @Singleton
    @Provides
    @Named("okHttpCookie")
    fun provideOkHttpCookieClient(okHttpClient: OkHttpClient): OkHttpClient {
        return okHttpClient
            .newBuilder()
            .cookieJar(
                JavaNetCookieJar(
                    CookieManager().apply {
                        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
                    }
                ))
            .build()
    }


    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()


    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BaseUrl.BASE_SERVER)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

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
            .defaultHttpCachePolicy(HttpCachePolicy.NETWORK_ONLY)
            .defaultResponseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
            .serverUrl("https://gql-dev.skuad.in/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiManager(retrofit: Retrofit): ApiManager = ApiManager(retrofit)

    @Singleton
    @Provides
    fun provideLoginApi(apiManager: ApiManager): LoginApi = apiManager.loginApi

    @Singleton
    @Provides
    fun provideDashboardRepo(apolloClient: ApolloClient): DashboardRepo = DashboardRepoImpl(apolloClient)

    @Singleton
    @Provides
    fun provideCandidateRepo(apolloClient: ApolloClient): CandidateRepo = CandidateRepoImpl(apolloClient)

    @Singleton
    @Provides
    fun provideLoginRepo(firebaseAuth: FirebaseAuth, prefs: SharedPrefRepoImpl, loginApi: LoginApi): LoginRepo = LoginRepoImpl(firebaseAuth, prefs, loginApi)
}