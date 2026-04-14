package sunlivecorp.mart.app.data.repository

import sunlivecorp.mart.app.data.datastore.CPNSLOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CPNSLOnboardingRepo(
    private val cpnslOnboardingStoreManager: CPNSLOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return cpnslOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            cpnslOnboardingStoreManager.setOnboardedState(state)
        }
    }
}