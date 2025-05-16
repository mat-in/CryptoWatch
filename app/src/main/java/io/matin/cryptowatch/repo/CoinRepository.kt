package io.matin.cryptowatch.repo

import androidx.room.util.query
import io.matin.cryptowatch.data.retrofit.CoinService
import io.matin.cryptowatch.data.retrofit.SearchCoinResponse
import io.matin.cryptowatch.data.retrofit.TrendingCoinResponse
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class CoinRepository @Inject constructor(private val coinService: CoinService) {

    suspend fun getTrending(): Response<TrendingCoinResponse>{
        return coinService.getTrending()
    }

    suspend fun getSearch(query: String): Response<SearchCoinResponse>{
        return coinService.getSearch(query)
    }

}