package com.stockbit.model.response

import com.google.gson.annotations.SerializedName

data class DataInfo (
    @SerializedName("CoinInfo") val coinInfo: CoinInfo,
    @SerializedName("RAW") val rawInfo: RawInfo
)

data class CoinInfo(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val symbol: String,
    @SerializedName("FullName") val description: String
)

data class DetailInfo(
    @SerializedName("PRICE") val price: Double,
    @SerializedName("CHANGEHOUR") val change: Double,
    @SerializedName("CHANGEPCTHOUR") val changePercentage: Double
)

data class RawInfo(
    @SerializedName("USD") val detailInfo: DetailInfo
)