package com.bravo.invoice

import android.app.Application
import com.bravo.basic.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class InvoiceMakerApplication : Application() {
    companion object {
        lateinit var app: InvoiceMakerApplication
    }

    init {
        app = this
    }
    override fun onCreate() {
        super.onCreate()

        if (Timber.treeCount == 0 && BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}