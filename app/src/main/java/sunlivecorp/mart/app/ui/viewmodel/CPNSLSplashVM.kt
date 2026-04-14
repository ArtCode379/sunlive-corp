package sunlivecorp.mart.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sunlivecorp.mart.app.data.repository.CPNSLOnboardingRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CPNSLSplashVM(
    private val onboardingRepository: CPNSLOnboardingRepo,
) : ViewModel() {
    val onboardedState: StateFlow<Boolean> =
        onboardingRepository.observeOnboardingState()
            .map { it == true }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = false
            )

}