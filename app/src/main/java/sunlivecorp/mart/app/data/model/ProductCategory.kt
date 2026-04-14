package sunlivecorp.mart.app.data.model

import androidx.annotation.StringRes
import sunlivecorp.mart.app.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    CLOTHING_SHOES(R.string.category_clothing_shoes),
    SPORTS_EQUIPMENT(R.string.category_sports_equipment),
    KIDS_TOYS(R.string.category_kids_toys),
    BOOKS_MEDIA(R.string.category_books_media),
}
