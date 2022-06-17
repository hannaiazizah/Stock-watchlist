package com.stockbit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class Watchlist(
    @PrimaryKey
    val id: Long,
    val symbol: String,
    val name: String,
    val price: String,
    val change: String,
    val changeAmount: Double,
    val changepct: String
)
