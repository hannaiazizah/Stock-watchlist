package com.stockbit.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.stockbit.model.Watchlist

@Dao
abstract class WatchlistDao: BaseDao<Watchlist>() {
    @Query("SELECT * FROM watchlist")
     abstract fun getWatchlist(): PagingSource<Int, Watchlist>

    suspend fun save(data: Watchlist) {
        insert(data)
    }

    suspend fun save(datas: List<Watchlist>) {
        insert(datas)
    }

    @Query("DELETE FROM watchlist")
    abstract suspend fun clearWatchlist()
}