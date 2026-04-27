package com.example.dapoer_mbg.util

import android.content.Context
import com.example.dapoer_mbg.model.Habit
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper(var context: Context) {
    val folderName = "habits"
    val fileName = "my habits.txt"
    private val gson = Gson()

    // function untuk bikin file baru/load file jika sudah ada
    private fun getFile(): File{
        val dir =  File(context.filesDir, folderName)
        if(!dir.exists()){
            dir.mkdir()
        }
        return File(dir, fileName)
    }

    // tulis string ke dalam file
    fun writeToFile(id: String, name: String, description: String, goal: Int, progress: Int, iconName: String, unit: String) {
        try {
            val data = "$id|$name|$description|$goal|$progress|$iconName|$unit\n"
            val file = getFile()
            FileOutputStream(file, true).use { output ->
                output.write(data.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Baca string dari file
    fun readFromFile(): String {
        return try {
            val file = getFile()
            file.bufferedReader().useLines { lines ->
                lines.joinToString("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace().toString()
        }
    }

    // Hapus file
    fun deleteFile(): Boolean {
        return getFile().delete()
    }

    // Menghasilkan string path menuju file
    fun getFilePath(): String {
        return getFile().absolutePath
    }

        fun getHabitsList(): MutableList<Habit>{
            val habitList = mutableListOf<Habit>()
            val file = getFile()

            if(!file.exists()){
                return habitList
            }

            val isiFile = file.readLines()
            if(isiFile.isEmpty()){
                return habitList
            }
            else{
                for(barisData in isiFile){
                    val parts = barisData.split("|")
                    if(parts.size==7){
                        val id = parts[0].toIntOrNull() ?: 0
                        val name = parts[1]
                        val description = parts[2]
                        val goal = parts[3].toIntOrNull() ?: 0
                        val progress = parts[4].toIntOrNull() ?: 0
                        val iconName = parts[5]
                        val unit = parts[6]
                        val habit = Habit(id, name, description, goal, progress, iconName, unit)
                        habitList.add(habit)
                    }
                }
            }
            return habitList
        }

    fun getHabitLastId(): Int{
        val file = getFile()
        if(!file.exists()){
            return 0
        }
        val isiFile = file.readLines()
        if(isiFile.isEmpty()){
            return 0
        }
        else{
            val barisTerakhir = isiFile.last()
            val parts = barisTerakhir.split("|")
            val lastId = parts[0].toInt()
            print("Last ID:$lastId")
            return lastId;
        }
    }

    fun saveHabitsList(habitList: List<Habit>) {
        try {
            val file = getFile()
            file.bufferedWriter().use { writer ->
                for (habit in habitList) {
                    writer.write("${habit.id}|${habit.name}|${habit.description}|${habit.goal}|${habit.progress}|${habit.iconName}|${habit.unit}")
                    writer.newLine()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}