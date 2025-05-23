package io.matin.cryptowatch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.matin.cryptowatch.data.retrofit.SearchCoin
import io.matin.cryptowatch.data.retrofit.SearchCoinResponse
import io.matin.cryptowatch.data.retrofit.TrendingCoinResponse
import io.matin.cryptowatch.repo.CoinRepository
import io.matin.cryptowatch.repo.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoiniViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val searchRepository: SearchRepository
): ViewModel() {

    //RETROFIT
    fun getTrending(): Flow<TrendingCoinResponse> = flow{
            val trendingResponse = coinRepository.getTrending()
            Log.d("CoiniViewModel", "API Response: ${trendingResponse.body()}")
            if (trendingResponse.isSuccessful && trendingResponse.body()!=null){
                delay(500L)
                emit(trendingResponse.body()!!)
            }else {
                throw Exception("Error fetching trending coins: ${trendingResponse.errorBody()?.string()}")
            }
    }.flowOn(Dispatchers.IO)

    fun getSearch(query: String): Flow<SearchCoinResponse> = flow {
        val searchResponse = coinRepository.getSearch(query)
        Log.d("CoiniViewModel", "API Response: ${searchResponse.body()}")
        if (searchResponse.isSuccessful && searchResponse.body()!=null){
            delay(500L)
            emit(searchResponse.body()!!)
        }else{
            throw Exception("Error fetching trending coins: ${searchResponse.errorBody()?.string()}")
        }
    }.flowOn(Dispatchers.IO)

    //ROOM
    val getAll = searchRepository.getAll
    fun insert(searchCoin: SearchCoin){
        viewModelScope.launch {
            searchRepository.insert(searchCoin)
        }
    }
    fun delete(searchCoin: SearchCoin){
        viewModelScope.launch {
            searchRepository.delete(searchCoin)
        }
    }

    fun getOHLCData(coinId: String, vsCurrency: String, days: Int): Flow<List<List<Double>>> = flow {
        val response = coinRepository.getOHLCData(coinId, vsCurrency, days)
        if (response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else {
            throw Exception("Failed to fetch OHLC data")
        }
    }.flowOn(Dispatchers.IO)

}