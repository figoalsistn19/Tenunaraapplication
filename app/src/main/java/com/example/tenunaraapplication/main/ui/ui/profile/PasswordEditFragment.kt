package com.example.tenunaraapplication.main.ui.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentPasswordEditBinding


class PasswordEditFragment : Fragment() {

    private var token = ""
    private var _binding: FragmentPasswordEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPasswordEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnBack.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_passwordEditFragment_to_profileEditFragment)
        }

        return root
    }
}