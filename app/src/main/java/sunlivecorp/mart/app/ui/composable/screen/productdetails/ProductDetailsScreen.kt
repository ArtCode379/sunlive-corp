package sunlivecorp.mart.app.ui.composable.screen.productdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import sunlivecorp.mart.app.R
import sunlivecorp.mart.app.data.model.Product
import sunlivecorp.mart.app.ui.composable.shared.CPNSLContentWrapper
import sunlivecorp.mart.app.ui.composable.shared.CPNSLEmptyView
import sunlivecorp.mart.app.ui.state.DataUiState
import sunlivecorp.mart.app.ui.viewmodel.ProductDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel(),
) {
    val productState by viewModel.productDetailsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeProductDetails(productId)
    }

    ProductDetailsScreenContent(
        productState = productState,
        modifier = modifier,
        onAddToCart = viewModel::addProductToCart
    )
}

@Composable
private fun ProductDetailsScreenContent(
    productState: DataUiState<Product>,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
) {
    Column(modifier = modifier) {

        CPNSLContentWrapper(
            dataState = productState,

            dataPopulated = {
                val data = (productState as DataUiState.Populated).data

            },

            dataEmpty = {
                CPNSLEmptyView(
                    primaryText = stringResource(R.string.product_details_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}