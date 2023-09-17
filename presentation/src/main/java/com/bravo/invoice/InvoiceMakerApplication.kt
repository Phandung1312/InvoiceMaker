package com.bravo.invoice

import android.app.Application
import com.bravo.domain.model.Client
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InvoiceMakerApplication : Application() {
    companion object {
        lateinit var app: InvoiceMakerApplication
    }
    var test :Client? = null
    init {
        app = this
    }
}