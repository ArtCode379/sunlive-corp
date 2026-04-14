package sunlivecorp.mart.app.di

import sunlivecorp.mart.app.data.repository.CartRepository
import sunlivecorp.mart.app.data.repository.CPNSLOnboardingRepo
import sunlivecorp.mart.app.data.repository.OrderRepository
import sunlivecorp.mart.app.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        CPNSLOnboardingRepo(
            cpnslOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}