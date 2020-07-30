package com.tonynowater.core.repository.network

import android.content.Context
import android.net.ConnectivityManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tonynowater.core.BuildConfig
import com.tonynowater.core.repository.ResourceRepository
import com.tonynowater.core.repository.ResourceRepositoryImpl
import com.tonynowater.core.repository.network.exception.ErrorHandler
import com.tonynowater.core.repository.network.exception.SimpleErrorHandler
import com.tonynowater.core.repository.network.monitor.NetworkStateMonitor
import com.tonynowater.core.repository.network.monitor.SimpleNetworkStateMonitor
import com.tonynowater.core.repository.network.retrofit.API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single { androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    single<ResourceRepository> {
        ResourceRepositoryImpl(
            api = get(),
            database = get()
        )
    }

    single<Interceptor>(named("http")) {

        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

    single<Converter.Factory>(named("moshi")) {
        MoshiConverterFactory.create(
            Moshi.Builder().add(
                KotlinJsonAdapterFactory()
            ).build()
        )
    }

    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

    factory {

        OkHttpClient()
            .newBuilder()
            .apply {

                if (BuildConfig.DEBUG) {
                    addInterceptor(get(named("http")))
                }

            }
            .build()
    }

    factory {

        Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(get(named("moshi")))
            .addCallAdapterFactory(get())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(API::class.java) }

    single<NetworkStateMonitor> {
        SimpleNetworkStateMonitor(
            context = androidContext(),
            manager = get()
        )
    }

    single<ErrorHandler> { SimpleErrorHandler(get()) }
}