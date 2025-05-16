package io.matin.cryptowatch.data.retrofit

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class SearchCoin(
    @PrimaryKey
    val id: String,
    val name: String?,
    val market_cap_rank: String?,
    val thumb: String?,
    val large: String?
)