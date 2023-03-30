package com.newsnow

import android.app.Application
import android.widget.Toast
import com.newsnow.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsNowApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Error handling for when Koin does not start
        try {
            startKoin {
                androidLogger()
                androidContext(this@NewsNowApplication)
                modules(appModule)
            }
        } catch (e: IllegalStateException) {
            Toast.makeText(
                this@NewsNowApplication,
                "Error starting Koin: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}