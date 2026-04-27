package com.example.dapoer_mbg.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dapoer_mbg.model.Habit
import com.example.dapoer_mbg.util.FileHelper

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val habitLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    //untuk crud
    private val fileHelper = FileHelper(application)

    fun refresh() {
        loadingLD.value = true
        habitLoadErrorLD.value = false

//        val habitList = arrayListOf(
//            Habit(1, "Morning Stretch", "Light stretching after waking up", 10, 4, "stretch", "minutes"),
//            Habit(2, "Read Articles", "Read educational articles daily", 5, 2, "read", "articles"),
//            Habit(3, "Drink Milk", "Drink milk for nutrition", 2, 1, "milk", "cups"),
//            Habit(4, "Jogging", "Morning jogging routine", 30, 10, "run", "minutes"),
//            Habit(5, "Practice Coding", "Solve coding problems", 3, 1, "code", "tasks"),
//            Habit(6, "Clean Room", "Keep room clean and tidy", 1, 0, "clean", "time"),
//            Habit(7, "Learn English", "Practice vocabulary and speaking", 20, 15, "study", "minutes"),
//            Habit(8, "Sleep Early", "Sleep before 11 PM", 1, 0, "sleep", "day")
//        )

        val habitList = fileHelper.getHabitsList()
        habitsLD.value = ArrayList(habitList)
        loadingLD.value = false
        habitLoadErrorLD.value = false
    }

    fun increaseProgress(position: Int) {
        val list = habitsLD.value
        list?.let {
            val habit = it[position]
            if (habit.progress < habit.goal) {
                habit.progress += 1
                habitsLD.value = ArrayList(it)
                fileHelper.saveHabitsList(it)
            }
        }
    }

    fun decreaseProgress(position: Int) {
        val list = habitsLD.value
        list?.let {
            val habit = it[position]
            if (habit.progress > 0) {
                habit.progress -= 1
                habitsLD.value = ArrayList(it)
                fileHelper.saveHabitsList(it)
            }
        }
    }
    fun getHabitLastId(): Int{
        val fileHelper = FileHelper(getApplication())
        val id = fileHelper.getHabitLastId()
        return id
    }

    fun createNewHabit(name:String, description:String, goal:Int, progress:Int, iconName:String, unit:String){
        val fileHelper = FileHelper(getApplication())
        val id = (getHabitLastId() + 1).toString()
        fileHelper.writeToFile(id, name, description, goal, progress,iconName,unit)
        val content = fileHelper.readFromFile()
        Log.d("print_file", content)
        Log.d("print_file", fileHelper.getFilePath())
    }





}