package com.tonynowater.core

import com.tonynowater.core.dialog.DialogManager
import com.tonynowater.core.dialog.MaterialDialogManager
import org.koin.dsl.module
import timber.log.Timber

val appModule = module {

    single<DialogManager> { MaterialDialogManager() }

    single {

        if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        }
    }
}