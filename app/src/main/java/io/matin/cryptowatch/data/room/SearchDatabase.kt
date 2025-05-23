package io.matin.cryptowatch.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.matin.cryptowatch.data.retrofit.SearchCoin

@Database(entities = [SearchCoin::class], version = 2)
abstract class SearchDatabase: RoomDatabase() {
    abstract fun searchDao(): SearchDAO

    companion object{
        @Volatile
        private var INSTANCE : SearchDatabase? = null
        fun getDatabase(context: Context): SearchDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SearchDatabase::class.java,
                    "search_database"
                ).fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}