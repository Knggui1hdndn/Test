package com.example.test.model.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.test.model.Animation


@Database(entities = [Animation::class], version =1,exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun dao() : AnimationDao

    companion object{

        @Volatile
        private var INSTANCE : DataBase? = null

        fun getDatabase(context: Context): DataBase{

            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "app_database"
                ). allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }

        }

    }

}