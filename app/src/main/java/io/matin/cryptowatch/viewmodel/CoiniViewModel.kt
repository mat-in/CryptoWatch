package io.matin.cryptowatch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.matin.cryptowatch.data.retrofit.SearchCoin
import io.matin.cryptowatch.data.retrofit.SearchCoinResponse
import io.matin.cryptowatch.data.retrofit.TrendingCoinResponse
import io.matin.cryptowatch.repo.CoinRepository
import io.matin.cryptowatch.repo.SearchRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoiniViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val searchRepository: SearchRepository
): ViewModel() {

    val trendingLiveData = MutableLiveData<TrendingCoinResponse>()
    val searchLiveData = MutableLiveData<SearchCoinResponse>()

    val getAll = searchRepository.getAll

    fun getTrending(){
        viewModelScope.launch {
            val trendingResponse = coinRepository.getTrending()
            Log.d("CoiniViewModel", "API Response: ${trendingResponse.body()}")
            try {
                trendingLiveData.postValue(trendingResponse.body())
            }
            catch(e: Exception){
                Log.e("Trending Error:", e.message.toString())
            }
        }
    }


    fun getSearch(query: String) {
        viewModelScope.launch {
            try {
                val response = coinRepository.getSearch(query)

                if (response.isSuccessful && response.body() != null) {
                    searchLiveData.postValue(response.body())
                    Log.d("Search", "Search success: ${response.body()}")
                } else {
                    Log.e("SearchError", "Response not successful: ${response.code()} ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e("SearchError", "Exception occurred", e)
            }
        }
    }

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
}