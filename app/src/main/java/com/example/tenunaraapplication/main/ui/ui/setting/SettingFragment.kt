package com.example.tenunaraapplication.main.ui.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentHomeBinding
import com.example.tenunaraapplication.databinding.FragmentSettingBinding
import com.example.tenunaraapplication.login.ActivityLogin

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}