package com.example.dapoer_mbg.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun registerUser(user: User): Long

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun login(username: String, password: String): User?
}