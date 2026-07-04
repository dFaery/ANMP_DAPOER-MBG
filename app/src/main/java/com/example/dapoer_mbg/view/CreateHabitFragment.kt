package com.example.dapoer_mbg.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dapoer_mbg.R
import com.example.dapoer_mbg.databinding.FragmentCreateHabitBinding
import com.example.dapoer_mbg.model.Habit
import com.example.dapoer_mbg.viewmodel.HabitViewModel

class CreateHabitFragment : Fragment() {
    private lateinit var binding: FragmentCreateHabitBinding

    private lateinit var habitViewModel: HabitViewModel

    private val iconOptions = arrayOf(
        "stretch", "read", "milk", "run", "code", "clean", "study", "sleep"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        val args = CreateHabitFragmentArgs.fromBundle(requireArguments())
        val habitId = args.habitId

        if (habitId == -1) {
            binding.habit = Habit(
                name = "",
                description = "",
                goal = 0,
                progress = 0,
                iconName = "",
                unit = ""
            )
        } else {
            val habit = habitViewModel.getHabitById(habitId)
            if (habit != null) {
                binding.habit = habit
                binding.tvTitle.text = "Update Habit"
                binding.btnCreateHabit.text = "Submit"
            }
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, iconOptions)
        binding.txtIcon.setAdapter(adapter)
        if (habitId != -1) {
            binding.habit?.let {
                binding.txtIcon.setText(it.iconName, false)
            }
        }

        binding.btnCreateHabit.setOnClickListener {
            if (habitId == -1) {
                val name = binding.txtHabitName.text.toString()
                val description = binding.txtShortDescription.text.toString()
                val goal = binding.txtGoalQty.text.toString().toIntOrNull() ?: 0
                val iconName = binding.txtIcon.text.toString()
                val unit = binding.txtUnit.text.toString()

                habitViewModel.createNewHabit(
                    name,
                    description,
                    goal,
                    0,
                    iconName,
                    unit
                )

                Toast.makeText(
                    requireContext(),
                    "Success add New habit!",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                binding.habit?.let {
                    it.goal = binding.txtGoalQty.text.toString().toIntOrNull() ?: 0
                    it.iconName = binding.txtIcon.text.toString()
                    habitViewModel.updateHabit(it)
                }

                Toast.makeText(
                    requireContext(),
                    "Habit updated!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            findNavController().navigateUp()
        }
    }


}