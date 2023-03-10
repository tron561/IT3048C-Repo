package com.newsnow

import android.app.Application
import com.newsnow.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class NewsNowApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        GlobalContext.startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@NewsNowApplication)
            modules(appModule)
        }
    }

}