package sunlivecorp.mart.app.di

import sunlivecorp.mart.app.ui.viewmodel.AppViewModel
import sunlivecorp.mart.app.ui.viewmodel.CartViewModel
import sunlivecorp.mart.app.ui.viewmodel.CheckoutViewModel
import sunlivecorp.mart.app.ui.viewmodel.CPNSLOnboardingVM
import sunlivecorp.mart.app.ui.viewmodel.OrderViewModel
import sunlivecorp.mart.app.ui.viewmodel.ProductDetailsViewModel
import sunlivecorp.mart.app.ui.viewmodel.ProductViewModel
import sunlivecorp.mart.app.ui.viewmodel.CPNSLSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        CPNSLSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        CPNSLOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}