package sunlivecorp.mart.app.ui.composable.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sunlivecorp.mart.app.R
import sunlivecorp.mart.app.data.entity.OrderEntity
import sunlivecorp.mart.app.ui.composable.shared.CPNSLContentWrapper
import sunlivecorp.mart.app.ui.composable.shared.CPNSLEmptyView
import sunlivecorp.mart.app.ui.state.DataUiState
import sunlivecorp.mart.app.ui.theme.BronzeAccent
import sunlivecorp.mart.app.ui.theme.CharcoalPrimary
import sunlivecorp.mart.app.ui.theme.OffWhiteBackground
import sunlivecorp.mart.app.ui.theme.SuccessGreen
import sunlivecorp.mart.app.ui.theme.TextMuted
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
    Column(modifier = modifier.fillMaxSize()) {
        CPNSLContentWrapper(
            dataState = ordersState,
            dataPopulated = {
                val data = (ordersState as DataUiState.Populated).data
                OrdersPopulated(orders = data)
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

@Composable
private fun OrdersPopulated(orders: List<OrderEntity>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(orders.sortedByDescending { it.timestamp }) { order ->
            OrderCard(order = order)
        }
    }
}

@Composable
private fun OrderCard(order: OrderEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order #${order.orderNumber}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CharcoalPrimary
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SuccessGreen.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(text = "Reserved", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = order.description, fontSize = 13.sp, color = TextMuted, maxLines = 2)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${order.customerFirstName} ${order.customerLastName}", fontSize = 13.sp, color = TextMuted)
                Text(text = "£%.2f".format(order.price), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BronzeAccent)
            }
        }
    }
}
