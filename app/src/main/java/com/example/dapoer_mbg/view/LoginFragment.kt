package com.example.dapoer_mbg.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dapoer_mbg.R
import com.example.dapoer_mbg.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.txtLogin.setOnClickListener {

            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            if (username == "student" && password == "123") {
                findNavController().navigate(R.id.actionDashboardFragment)
            } else {
                Toast.makeText(requireContext(), "Username / Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}