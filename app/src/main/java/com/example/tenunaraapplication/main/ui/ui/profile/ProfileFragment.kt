package com.example.tenunaraapplication.main.ui.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvNameProfile
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEditProfile.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_navigation_profile_to_profileEditFragment)
        )

        binding.btnLogout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_profile_to_activityLogin)
        }

        binding.btnSetting.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_navigation_profile_to_settingFragment)
            )


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}