package com.example.dapoer_mbg.model

data class Habit(
    var id: Int,
    var name: String,
    var description: String,
    var goal: Int,
    var progress: Int,
    var iconName: String,
    var unit: String
)