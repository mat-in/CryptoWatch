package io.matin.cryptowatch.repo

import androidx.lifecycle.LiveData
import io.matin.cryptowatch.data.retrofit.SearchCoin
import io.matin.cryptowatch.data.room.SearchDAO
import javax.inject.Inject


class SearchRepository @Inject constructor(private val searchDAO: SearchDAO) {

    suspend fun insert(searchCoin: SearchCoin){
        searchDAO.insertSearch(searchCoin)
    }

    val getAll: LiveData<List<SearchCoin>> = searchDAO.getAllSearch()

    suspend fun delete(searchCoin: SearchCoin){
        searchDAO.deleteSearch(searchCoin)
    }
}