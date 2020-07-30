package com.tonynowater.mypokedex.app.initializer

import android.content.Context
import androidx.startup.Initializer
import com.tonynowater.core.appModule
import com.tonynowater.core.repository.database.databaseModule
import com.tonynowater.core.repository.network.networkModule
import com.tonynowater.mypokedex.home.homeModule
import com.tonynowater.mypokedex.detail.nextModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class KoinInitializer: Initializer<Unit> {

    override fun create(appContext: Context) {
        startKoin {
            androidLogger()
            androidContext(appContext)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    databaseModule,
                    homeModule,
                    nextModule
                )
            )
        }
        Timber.d("[DEBUG] KoinInitializer onCreate")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(TimberInitializer::class.java)
}