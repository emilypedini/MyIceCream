package com.example.myicecream.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class IceCreamDatabase :  RoomDatabase(){

    abstract fun userDAO(): UserDAO
    abstract fun postDAO(): PostDAO

    companion object {
        @Volatile
        private var INSTANCE: IceCreamDatabase? = null

        fun getDatabase(context: Context): IceCreamDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IceCreamDatabase::class.java,
                    "icecream_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}