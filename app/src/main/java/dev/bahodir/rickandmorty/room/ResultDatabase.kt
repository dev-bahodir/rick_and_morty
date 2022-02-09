package dev.bahodir.rickandmorty.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Result::class], version = 1)
abstract class ResultDatabase : RoomDatabase() {
    abstract fun getResultDao() : ResultDao

    companion object {
        private var instance : ResultDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : ResultDatabase {
            if (instance == null) {
                instance =  Room
                    .databaseBuilder(context, ResultDatabase::class.java, "result.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}