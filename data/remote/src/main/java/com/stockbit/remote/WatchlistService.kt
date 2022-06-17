package com.stockbit.remote

import com.stockbit.model.response.WatchlistResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchlistService {
    @GET("top/totaltoptiervolfull?tsym=USD")
    suspend fun fetchCryptoWatchlist(
        @Query("page") page: Int,
        @Query("limit") itemsPerPage: Int
    ): Response<WatchlistResponse>
}