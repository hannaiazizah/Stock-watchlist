package com.stockbit.model.response

import com.google.gson.annotations.SerializedName

data class MetadataInfo(
    @SerializedName("Count") val count: Int
)