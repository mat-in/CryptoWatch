package io.matin.cryptowatch.data.retrofit

import io.matin.cryptowatch.data.retrofit.SearchCoinResponse
import io.matin.cryptowatch.data.retrofit.TrendingCoinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinService {
    @GET("search/trending")
    suspend fun getTrending(): Response<TrendingCoinResponse>

    @GET("search")
    suspend fun getSearch(
        @Query("query") query: String
    ): Response<SearchCoinResponse>

//    @GET("coins/{id}/market_chart")
//    suspend fun getDetail(
//        @Path("id") id: String,
//        @Query("vs_currency") vsCurrency: String,
//        @Query("days") days: String
//    )
}