package sunlivecorp.mart.app.di

import androidx.room.Room
import sunlivecorp.mart.app.data.database.CPNSLDatabase
import org.koin.dsl.module

private const val DB_NAME = "cpnsl_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = CPNSLDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<CPNSLDatabase>().cartItemDao() }

    single { get<CPNSLDatabase>().orderDao() }
}