package com.stockbit.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stockbit.local.converter.Converters
import com.stockbit.local.dao.ExampleDao
import com.stockbit.local.dao.RemoteKeysDao
import com.stockbit.local.dao.WatchlistDao
import com.stockbit.model.ExampleModel
import com.stockbit.model.RemoteKeys
import com.stockbit.model.Watchlist

@Database(
    entities = [
        ExampleModel::class,
        Watchlist::class,
        RemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    // DAO
    abstract fun exampleDao(): ExampleDao
    abstract fun watchlistDao(): WatchlistDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "App.db")
                .build()
    }
}