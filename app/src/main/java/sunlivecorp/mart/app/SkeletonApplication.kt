package sunlivecorp.mart.app

import android.app.Application
import sunlivecorp.mart.app.di.dataModule
import sunlivecorp.mart.app.di.dispatcherModule
import sunlivecorp.mart.app.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CPNSLApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@CPNSLApplication)
            modules(appModules)
        }
    }
}