package com.example.dapoer_mbg.view

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dapoer_mbg.R
import com.example.dapoer_mbg.databinding.HabitItemCardBinding
import com.example.dapoer_mbg.model.Habit

class HabitListAdapter(val habitList: ArrayList<Habit>, val onPlusClick: (Int) -> Unit, val onMinusClick: (Int) -> Unit)
    : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    class HabitViewHolder(var binding: HabitItemCardBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        holder.binding.txtHabitName.text = habit.name
        holder.binding.txtHabitDesc.text = habit.description
        holder.binding.txtProgressValue.text = "${habit.progress} / ${habit.goal} ${habit.unit}"

        holder.binding.progressBarHabit.max = habit.goal
        holder.binding.progressBarHabit.progress = habit.progress

        if (habit.progress >= habit.goal) {
            holder.binding.txtStatus.text = "Completed"
            holder.binding.cardHabit.strokeColor =
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark)
           holder.binding.txtStatus.setBackgroundResource(R.drawable.bg_completed)
            holder.binding.txtStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.white))
           holder.binding.progressBarHabit.progressTintList =
                ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark))
        } else {
            holder.binding.txtStatus.text = "In Progress"
            holder.binding.cardHabit.strokeColor =
                ContextCompat.getColor(holder.itemView.context, android.R.color.transparent)
            holder.binding.txtStatus.setBackgroundResource(0)
            holder.binding.txtStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.black))
            holder.binding.progressBarHabit.progressTintList = null
        }

        val iconRes = when (habit.iconName) {
            "stretch" -> R.drawable.stretch
            "read" -> R.drawable.read
            "milk" -> R.drawable.milk
            "run" -> R.drawable.run
            "code" -> R.drawable.code
            "clean" -> R.drawable.clean
            "study" -> R.drawable.study
            "sleep" -> R.drawable.sleep
            else -> R.drawable.ic_launcher_foreground
        }

        holder.binding.imgHabitIcon.setImageResource(iconRes)


        holder.binding.btnPlus.setOnClickListener {
            onPlusClick(position)
        }

        holder.binding.btnMinus.setOnClickListener {
            onMinusClick(position)
        }
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}