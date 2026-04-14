package sunlivecorp.mart.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val CPNSL_PREFS_NAME = "cpnsl_prefs"

val Context.cpnslOnboardingStore by preferencesDataStore(name = CPNSL_PREFS_NAME)

class CPNSLOnboardingPrefs(
    private val context: Context
) {
    val onboardedStateFlow: Flow<Boolean?> = context.cpnslOnboardingStore.data.map { prefs ->
        prefs[ONBOARDED_STATE_KEY]
    }

    suspend fun setOnboardedState(state: Boolean) {
        context.cpnslOnboardingStore.edit { prefs ->
            prefs[ONBOARDED_STATE_KEY] = state
        }
    }

    companion object {
        private val ONBOARDED_STATE_KEY = booleanPreferencesKey("onboardedState")
    }
}