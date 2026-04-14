package sunlivecorp.mart.app.di

import sunlivecorp.mart.app.data.datastore.CPNSLOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { CPNSLOnboardingPrefs(androidContext()) }
}