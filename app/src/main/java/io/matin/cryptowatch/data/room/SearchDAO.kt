package io.matin.cryptowatch.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.matin.cryptowatch.data.retrofit.SearchCoin

@Dao
interface SearchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchCoin: SearchCoin)

    @Query("SELECT * FROM SearchCoin")
    fun getAllSearch(): LiveData<List<SearchCoin>>

    @Delete
    suspend fun deleteSearch(searchCoin: SearchCoin)
}