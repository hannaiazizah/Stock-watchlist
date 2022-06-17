package com.stockbit.common.utils

import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Currency

fun Double.toCurrency(): String {
    val format = NumberFormat.getCurrencyInstance();
    format.maximumFractionDigits = 2;
    format.currency = Currency.getInstance("USD");

    return format.format(this.toBigDecimal())
}

fun Double.toPercentage(): String {
    val value = this.toBigDecimal().setScale(3, RoundingMode.HALF_UP).stripTrailingZeros()
    return "$value%"
}

fun Double.toChange(): String {
    val value = this.toBigDecimal().setScale(3, RoundingMode.HALF_UP).stripTrailingZeros()
    return value.toString()
}