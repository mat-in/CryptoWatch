package io.matin.cryptowatch.data.retrofit

data class TrendingCoin(
    val id: String,
    val name: String,
    val thumb: String,
    val price_btc: Double
)