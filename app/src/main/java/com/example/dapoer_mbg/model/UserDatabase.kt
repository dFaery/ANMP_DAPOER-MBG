package com.example.dapoer_mbg.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        Habit::class], version = 2)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun habitDao(): HabitDao

    companion object{
        @Volatile private var instance: UserDatabase ?= null
        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "habitdb"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        operator fun invoke(context: Context): UserDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}