package com.example.dapoer_mbg.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dapoer_mbg.R
import com.example.dapoer_mbg.databinding.FragmentLoginBinding
import com.example.dapoer_mbg.model.User
import com.example.dapoer_mbg.model.UserDatabase

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = UserDatabase(requireContext())
        val dummyUser = User("student", "123")

        db.userDao().registerUser(dummyUser)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.txtLogin.setOnClickListener {

            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            val db = UserDatabase(requireContext())

            val user = db.userDao().login(username, password)

            if(user != null){
                Log.d("login_status", "Success, ${user.username}")
                Toast.makeText(requireContext(), "Login Sukses!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.actionDashboardFragment)
            }
            else{
                Log.d("login_status", "Failed")
                Toast.makeText(requireContext(), "Username / Password salah", Toast.LENGTH_SHORT).show()
            }
//            if (username == "student" && password == "123") {
//                findNavController().navigate(R.id.actionDashboardFragment)
//            } else {
//                Toast.makeText(requireContext(), "Username / Password salah", Toast.LENGTH_SHORT).show()
//            }
        }

        return binding.root
    }
}