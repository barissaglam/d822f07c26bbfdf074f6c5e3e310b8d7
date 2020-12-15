package barissaglam.challenge

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) setupDebugTools()
    }

    private fun setupDebugTools() {
        Stetho.initializeWithDefaults(this)
    }
}