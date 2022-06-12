package com.example.tenunaraapplication.main.ui.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentHomeBinding
import com.example.tenunaraapplication.databinding.FragmentProfileEditBinding

class ProfileEditFragment : Fragment() {

    private var token = ""
    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnBack.setOnClickListener {
            backToProfile()
        }

        binding.btnEditPw.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_profileEditFragment_to_passwordEditFragment)
        }



        return root
    }

    private fun backToProfile() {
        val navigationAction = ProfileEditFragmentDirections
            .actionProfileEditFragmentToNavigationProfile()
        navigationAction.token = token

        findNavController().navigate(navigationAction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}