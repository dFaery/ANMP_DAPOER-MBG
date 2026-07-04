package com.example.dapoer_mbg.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dapoer_mbg.R
import com.example.dapoer_mbg.databinding.FragmentDashboardBinding
import com.example.dapoer_mbg.viewmodel.HabitViewModel
import androidx.navigation.fragment.findNavController

class DashboardFragment : Fragment() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        habitListAdapter = HabitListAdapter(
            arrayListOf(),
            { position -> viewModel.increaseProgress(position) },
            { position -> viewModel.decreaseProgress(position) },
            { habit ->
                val action =
                    DashboardFragmentDirections.actionEditHabitFragment(habit.id)
                findNavController().navigate(action)
            }
        )

        //viewModel.refresh()

        binding.recViewHabit.layoutManager = LinearLayoutManager(context)
        binding.recViewHabit.adapter = habitListAdapter

        binding.fabAddHabit.setOnClickListener {
            val action = DashboardFragmentDirections.actionCreateHabitFragment(-1)
            findNavController().navigate(action)
        }
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    fun observeViewModel() {
        viewModel.habitsLD.observe(viewLifecycleOwner) {
            habitListAdapter.updateHabitList(it)

            if (it.isEmpty()) {
                binding.recViewHabit.visibility = View.GONE
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = "No habits yet"
            } else {
                binding.recViewHabit.visibility = View.VISIBLE
                binding.txtError.visibility = View.GONE
            }
        }

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recViewHabit.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recViewHabit.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }
}