package sunlivecorp.mart.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sunlivecorp.mart.app.data.dao.CartItemDao
import sunlivecorp.mart.app.data.dao.OrderDao
import sunlivecorp.mart.app.data.database.converter.Converters
import sunlivecorp.mart.app.data.entity.CartItemEntity
import sunlivecorp.mart.app.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CPNSLDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}