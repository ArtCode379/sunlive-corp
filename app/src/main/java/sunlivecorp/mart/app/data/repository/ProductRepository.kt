package sunlivecorp.mart.app.data.repository

import sunlivecorp.mart.app.R
import sunlivecorp.mart.app.data.model.Product
import sunlivecorp.mart.app.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products: List<Product> = listOf(
        Product(
            id = 1,
            title = "Vintage Denim Jacket",
            description = "Classic 90s cut denim jacket in excellent condition. Minor fading adds character. Size M. Perfect for layering in autumn and winter.",
            category = ProductCategory.CLOTHING_SHOES,
            price = 28.00,
            imageRes = R.drawable.product_1
        ),
        Product(
            id = 2,
            title = "Nike Air Running Shoes (EU 42)",
            description = "Pre-owned Nike Air running shoes with plenty of life left. Light wear on soles. Original laces included. Ideal for casual or light jogging.",
            category = ProductCategory.CLOTHING_SHOES,
            price = 35.00,
            imageRes = R.drawable.product_2
        ),
        Product(
            id = 3,
            title = "Kids Bicycle 20-Inch",
            description = "Sturdy second-hand kids bicycle, suitable for ages 6–9. Comes with stabilisers, front basket, and bell. Recently serviced and in great riding condition.",
            category = ProductCategory.KIDS_TOYS,
            price = 45.00,
            imageRes = R.drawable.product_3
        ),
        Product(
            id = 4,
            title = "Yoga Mat & Blocks Set",
            description = "Non-slip yoga mat (6mm thick) with two cork yoga blocks. Lightly used, no tears or stains. Perfect for home yoga or Pilates practice.",
            category = ProductCategory.SPORTS_EQUIPMENT,
            price = 18.00,
            imageRes = R.drawable.product_4
        ),
        Product(
            id = 5,
            title = "Vinyl Record Collection (20 LPs)",
            description = "Curated selection of 20 vinyl records spanning jazz, soul, and classic rock from the 1970s–90s. Records play cleanly with minor surface marks. Sleeves intact.",
            category = ProductCategory.BOOKS_MEDIA,
            price = 40.00,
            imageRes = R.drawable.product_5
        ),
        Product(
            id = 6,
            title = "Football Boots (EU 43)",
            description = "Pre-owned firm-ground football boots in very good condition. Suitable for grass pitches. Size EU 43 / UK 9. Cleaned and deodorised.",
            category = ProductCategory.SPORTS_EQUIPMENT,
            price = 22.00,
            imageRes = R.drawable.product_6
        ),
        Product(
            id = 7,
            title = "Classic Literature Box Set (12 books)",
            description = "Curated collection of 12 classic novels including works by Dickens, Tolstoy, Austen, and Brontë. Hardback editions in good condition with minimal highlighting.",
            category = ProductCategory.BOOKS_MEDIA,
            price = 15.00,
            imageRes = R.drawable.product_7
        ),
        Product(
            id = 8,
            title = "Toddler Wooden Play Kitchen",
            description = "Solid wooden play kitchen set for toddlers aged 2–5. Includes pretend cooker, sink, and 12 wooden food accessories. Clean and in excellent condition.",
            category = ProductCategory.KIDS_TOYS,
            price = 30.00,
            imageRes = R.drawable.product_8
        ),
    )

    fun observeById(id: Int): Flow<Product?> {
        val item = products.find { it.id == id }
        return flowOf(item)
    }

    fun getById(id: Int): Product? {
        return products.find { it.id == id }
    }

    fun observeAll(): Flow<List<Product>> {
        return flowOf(products)
    }
}
