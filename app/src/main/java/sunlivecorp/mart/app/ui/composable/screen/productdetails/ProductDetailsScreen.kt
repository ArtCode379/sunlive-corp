package sunlivecorp.mart.app.ui.composable.screen.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import sunlivecorp.mart.app.data.model.Product
import sunlivecorp.mart.app.ui.composable.shared.CPNSLContentWrapper
import sunlivecorp.mart.app.ui.composable.shared.CPNSLEmptyView
import sunlivecorp.mart.app.ui.state.DataUiState
import sunlivecorp.mart.app.ui.theme.BronzeAccent
import sunlivecorp.mart.app.ui.theme.CharcoalPrimary
import sunlivecorp.mart.app.ui.theme.OffWhiteBackground
import sunlivecorp.mart.app.ui.theme.SurfaceVariantCream
import sunlivecorp.mart.app.ui.theme.TextMuted
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
                ProductDetailContent(product = data, onAddToCart = onAddToCart)
            },
            dataEmpty = {
                CPNSLEmptyView(
                    primaryText = "No information available for this product",
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ProductDetailContent(
    product: Product,
    onAddToCart: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero image
        AsyncImage(
            model = product.imageRes,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
        )

        Column(modifier = Modifier.padding(20.dp)) {
            // Category chip
            SuggestionChip(
                onClick = {},
                label = {
                    Text(
                        text = stringResource(product.category.titleRes),
                        fontSize = 12.sp,
                        color = CharcoalPrimary
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = SurfaceVariantCream
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Name
            Text(
                text = product.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = CharcoalPrimary,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Price
            Text(
                text = "£%.2f".format(product.price),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BronzeAccent
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = "About this item",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = CharcoalPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.description,
                fontSize = 14.sp,
                color = TextMuted,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Eco badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceVariantCream)
                    .padding(14.dp)
            ) {
                Text(text = "♻️", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "Pre-loved item", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                    Text(text = "Buying second-hand reduces waste and supports conscious consumption.", fontSize = 12.sp, color = TextMuted, lineHeight = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = CharcoalPrimary)
            ) {
                Text(
                    text = stringResource(sunlivecorp.mart.app.R.string.button_add_to_cart_label),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
