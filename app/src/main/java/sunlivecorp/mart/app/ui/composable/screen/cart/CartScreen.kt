package sunlivecorp.mart.app.ui.composable.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import sunlivecorp.mart.app.R
import sunlivecorp.mart.app.ui.composable.shared.CPNSLContentWrapper
import sunlivecorp.mart.app.ui.composable.shared.CPNSLEmptyView
import sunlivecorp.mart.app.ui.state.CartItemUiState
import sunlivecorp.mart.app.ui.state.DataUiState
import sunlivecorp.mart.app.ui.theme.BorderLight
import sunlivecorp.mart.app.ui.theme.BronzeAccent
import sunlivecorp.mart.app.ui.theme.CharcoalPrimary
import sunlivecorp.mart.app.ui.theme.OffWhiteBackground
import sunlivecorp.mart.app.ui.theme.TextMuted
import sunlivecorp.mart.app.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val cartItemsState by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    CartScreenContent(
        cartItemsState = cartItemsState,
        modifier = modifier,
        totalPrice = totalPrice,
        onPlusItemClick = { viewModel.incrementProductInCart(it) },
        onMinusItemClick = { viewModel.decrementItemInCart(it) },
        onCompleteOrderButtonClick = onNavigateToCheckoutScreen,
    )
}

@Composable
private fun CartScreenContent(
    cartItemsState: DataUiState<List<CartItemUiState>>,
    modifier: Modifier = Modifier,
    totalPrice: Double,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
    onCompleteOrderButtonClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        CPNSLContentWrapper(
            dataState = cartItemsState,
            dataPopulated = {
                val data = (cartItemsState as DataUiState.Populated).data
                CartPopulated(
                    items = data,
                    totalPrice = totalPrice,
                    onPlusClick = onPlusItemClick,
                    onMinusClick = onMinusItemClick,
                    onCheckoutClick = onCompleteOrderButtonClick,
                    modifier = Modifier.weight(1f),
                )
            },
            dataEmpty = {
                CPNSLEmptyView(
                    primaryText = stringResource(R.string.cart_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun CartPopulated(
    items: List<CartItemUiState>,
    totalPrice: Double,
    onPlusClick: (Int) -> Unit,
    onMinusClick: (Int) -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { item ->
                CartItemRow(
                    item = item,
                    onPlusClick = { onPlusClick(item.productId) },
                    onMinusClick = { onMinusClick(item.productId) }
                )
            }
        }

        // Summary
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            HorizontalDivider(color = BorderLight, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Subtotal", fontSize = 14.sp, color = TextMuted)
                Text(text = "£%.2f".format(totalPrice), fontSize = 14.sp, color = TextMuted)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                Text(text = "£%.2f".format(totalPrice), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
            }
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onCheckoutClick,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = CharcoalPrimary)
            ) {
                Text(text = "Proceed to Checkout", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItemUiState,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item.productImageRes != null) {
                AsyncImage(
                    model = item.productImageRes,
                    contentDescription = item.productTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp))
                )
            }
            Column(
                modifier = Modifier.weight(1f).padding(start = 12.dp)
            ) {
                Text(text = item.productTitle, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CharcoalPrimary, maxLines = 2)
                Text(text = "£%.2f".format(item.productPrice), fontSize = 13.sp, color = BronzeAccent, fontWeight = FontWeight.Bold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onMinusClick, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Remove, contentDescription = null, tint = CharcoalPrimary, modifier = Modifier.size(16.dp))
                }
                Text(text = "${item.quantity}", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                IconButton(onClick = onPlusClick, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = CharcoalPrimary, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
