package com.example.dapoer_mbg.view

import android.view.View
import com.example.dapoer_mbg.model.Habit

interface HabitListListener {
    fun onPlusClick(v: View, habit: Habit)
    fun onMinusClick(v: View, habit: Habit)
}