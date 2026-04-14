package sunlivecorp.mart.app.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import sunlivecorp.mart.app.data.model.Product
import sunlivecorp.mart.app.data.model.ProductCategory
import sunlivecorp.mart.app.ui.composable.shared.CPNSLContentWrapper
import sunlivecorp.mart.app.ui.composable.shared.CPNSLEmptyView
import sunlivecorp.mart.app.ui.state.DataUiState
import sunlivecorp.mart.app.ui.theme.BorderLight
import sunlivecorp.mart.app.ui.theme.BronzeAccent
import sunlivecorp.mart.app.ui.theme.CharcoalPrimary
import sunlivecorp.mart.app.ui.theme.OffWhiteBackground
import sunlivecorp.mart.app.ui.theme.SurfaceVariantCream
import sunlivecorp.mart.app.ui.theme.TextMuted
import sunlivecorp.mart.app.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()

    HomeContent(
        productsState = productsState,
        modifier = modifier,
        onNavigateToProductDetails = onNavigateToProductDetails,
        onAddProductToCart = viewModel::addToCart,
    )
}

@Composable
private fun HomeContent(
    productsState: DataUiState<List<Product>>,
    modifier: Modifier = Modifier,
    onNavigateToProductDetails: (productId: Int) -> Unit,
    onAddProductToCart: (productId: Int) -> Unit,
) {
    CPNSLContentWrapper(
        dataState = productsState,
        dataPopulated = {
            val data = (productsState as DataUiState.Populated).data
            ProductsPopulated(
                products = data,
                onNavigateToProductDetails = onNavigateToProductDetails,
                modifier = modifier,
            )
        },
        dataEmpty = {
            CPNSLEmptyView(
                primaryText = "No products available",
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ProductsPopulated(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val featured = products.take(4)
    val pagerState = rememberPagerState(pageCount = { featured.size })
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
    val filtered = if (selectedCategory == null) products else products.filter { it.category == selectedCategory }

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000L)
            val next = (pagerState.currentPage + 1) % featured.size
            pagerState.animateScrollToPage(next)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Hero carousel
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            HorizontalPager(state = pagerState) { page ->
                val product = featured[page]
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = product.imageRes,
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                                    startY = 60f
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                    ) {
                        Text(text = product.title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(text = "£%.2f".format(product.price), color = BronzeAccent, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Row(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                repeat(featured.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (pagerState.currentPage == index) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(if (pagerState.currentPage == index) BronzeAccent else Color.White.copy(alpha = 0.5f))
                    )
                }
            }
        }

        // Category chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                CategoryChip(
                    label = "All",
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null }
                )
            }
            items(ProductCategory.entries) { cat ->
                CategoryChip(
                    label = stringResource(cat.titleRes),
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = if (selectedCategory == cat) null else cat }
                )
            }
        }

        // Product grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filtered) { product ->
                ProductCard(
                    product = product,
                    onClick = { onNavigateToProductDetails(product.id) }
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) CharcoalPrimary else SurfaceVariantCream)
            .border(1.dp, if (selected) CharcoalPrimary else BorderLight, RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (selected) Color.White else CharcoalPrimary,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = product.imageRes,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceVariantCream)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = stringResource(product.category.titleRes),
                        fontSize = 9.sp,
                        color = TextMuted
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = CharcoalPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "£%.2f".format(product.price),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BronzeAccent
                )
            }
        }
    }
}
