package com.example.dapoer_mbg.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dapoer_mbg.model.Habit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import com.example.dapoer_mbg.model.UserDatabase
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        launch {
            loadingLD.postValue(true)
            habitLoadErrorLD.postValue(false)

            val db = UserDatabase(getApplication())

            habitsLD.postValue(
                ArrayList(db.habitDao().getAllHabits())
            )

            loadingLD.postValue(false)
            habitLoadErrorLD.postValue(false)
        }
    }

    fun increaseProgress(position: Int) {
        launch {
            val list = habitsLD.value ?: return@launch
            val habit = list[position]

            if (habit.progress < habit.goal) {
                val newProgress = habit.progress + 1

                val db = UserDatabase(getApplication())
                db.habitDao().updateProgress(habit.id, newProgress)

                refresh()
            }
        }
    }

    fun decreaseProgress(position: Int) {
        launch {
            val list = habitsLD.value ?: return@launch
            val habit = list[position]

            if (habit.progress > 0) {
                val newProgress = habit.progress - 1

                val db = UserDatabase(getApplication())
                db.habitDao().updateProgress(habit.id, newProgress)

                refresh()
            }
        }
    }

    fun createNewHabit(
        name: String,
        description: String,
        goal: Int,
        progress: Int,
        iconName: String,
        unit: String
    ) {
        launch {
            val db = UserDatabase(getApplication())

            val habit = Habit(
                name = name,
                description = description,
                goal = goal,
                progress = progress,
                iconName = iconName,
                unit = unit
            )

            db.habitDao().insertHabit(habit)

            refresh()
        }
    }

    fun getHabitById(id: Int): Habit? {
        val db = UserDatabase(getApplication())
        return db.habitDao().getHabitById(id)
    }

    fun updateHabit(habit: Habit) {
        launch {
            val db = UserDatabase(getApplication())
            db.habitDao().updateHabit(habit)
            refresh()
        }
    }

    fun createNewHabit(habit: Habit) {
        launch {
            val db = UserDatabase(getApplication())
            db.habitDao().insertHabit(habit)
            refresh()
        }
    }





}