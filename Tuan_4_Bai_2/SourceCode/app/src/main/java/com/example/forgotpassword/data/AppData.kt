package com.example.forgotpassword.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppData : RoomDatabase(){
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: AppData? = null
        fun getDatabase(context: Context): AppData {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppData::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}