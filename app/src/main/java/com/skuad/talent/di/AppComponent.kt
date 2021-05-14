package com.skuad.talent.di

import android.app.Application
import com.skuad.talent.SkuadApp
import com.skuad.talent.di.module.ActivityBinder
import com.skuad.talent.di.module.NetworkBinder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBinder::class,
        NetworkBinder::class,

    ]
)
interface AppComponent : AndroidInjector<SkuadApp> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}