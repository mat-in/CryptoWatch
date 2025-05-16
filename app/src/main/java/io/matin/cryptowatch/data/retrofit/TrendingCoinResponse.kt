package io.matin.cryptowatch.data.retrofit

import io.matin.cryptowatch.data.retrofit.TrendingCoinWrapper

data class TrendingCoinResponse(
    val coins: List<TrendingCoinWrapper>
)