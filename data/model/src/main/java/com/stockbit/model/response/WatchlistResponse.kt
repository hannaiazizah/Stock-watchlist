package com.stockbit.model.response

import com.google.gson.annotations.SerializedName
import com.stockbit.model.response.DataInfo
import com.stockbit.model.response.MetadataInfo

data class WatchlistResponse (
    @SerializedName("MetaData") val metadataInfo: MetadataInfo,
    @SerializedName("Data") val watchlist: List<DataInfo>
)