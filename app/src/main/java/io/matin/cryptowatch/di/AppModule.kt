package io.matin.cryptowatch.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.matin.cryptowatch.BuildConfig
import io.matin.cryptowatch.data.retrofit.CoinService
import io.matin.cryptowatch.data.room.SearchDAO
import io.matin.cryptowatch.data.room.SearchDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val BASEURL = "https://api.coingecko.com/api/v3/"

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinGeckoApi(retrofit: Retrofit): CoinService =
        retrofit.create(CoinService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):
            SearchDatabase{
        return SearchDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideSearchDao(searchDatabase: SearchDatabase):
            SearchDAO {
        return searchDatabase.searchDao()
    }

}