package com.example.dapoer_mbg.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM Habit")
    fun getAllHabits(): List<Habit>

    @Query("SELECT * FROM Habit WHERE id = :id")
    fun getHabitById(id: Int): Habit?

    @Query("UPDATE Habit SET progress = :newProgress WHERE id = :id")
    fun updateProgress(id: Int, newProgress: Int)

    @Query("DELETE FROM Habit")
    fun deleteAllHabits()
}