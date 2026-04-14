package sunlivecorp.mart.app.ui.composable.screen.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import sunlivecorp.mart.app.R
import sunlivecorp.mart.app.data.entity.OrderEntity
import sunlivecorp.mart.app.ui.composable.shared.CPNSLContentWrapper
import sunlivecorp.mart.app.ui.composable.shared.CPNSLEmptyView
import sunlivecorp.mart.app.ui.state.DataUiState
import sunlivecorp.mart.app.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    OrdersContent(
        ordersState = ordersState,
        modifier = modifier,
    )
}

@Composable
private fun OrdersContent(
    ordersState: DataUiState<List<OrderEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {

        CPNSLContentWrapper(
            dataState = ordersState,

            dataPopulated = {
                val data = (ordersState as DataUiState.Populated).data

            },

            dataEmpty = {
                CPNSLEmptyView(
                    primaryText = stringResource(R.string.orders_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}