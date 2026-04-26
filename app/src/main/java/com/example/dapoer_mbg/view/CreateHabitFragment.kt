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
import com.example.dapoer_mbg.R
import com.example.dapoer_mbg.databinding.FragmentCreateHabitBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, iconOptions)
        binding.txtIcon.setAdapter(adapter)

        binding.btnCreateHabit.setOnClickListener {
            val name = binding.txtHabitName.text.toString()
            val description = binding.txtShortDescription.text.toString()
            val goal = binding.txtGoalQty.text.toString().toIntOrNull()?:0
            val iconName = binding.txtIcon.text.toString()
            val unit = binding.txtUnit.text.toString()
            val progress = 0

            habitViewModel.createNewHabit(name, description, goal, progress, iconName,unit)
            Toast.makeText(requireContext(), "Success add New habit!", Toast.LENGTH_SHORT).show()
        }
    }


}