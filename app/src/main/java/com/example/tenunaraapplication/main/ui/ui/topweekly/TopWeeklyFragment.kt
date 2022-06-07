package com.example.tenunaraapplication.main.ui.ui.topweekly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentProfileEditBinding
import com.example.tenunaraapplication.databinding.FragmentTopWeeklyBinding

class TopWeeklyFragment : Fragment() {

    private var token = ""
    private var _binding: FragmentTopWeeklyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      _binding = FragmentTopWeeklyBinding.inflate(inflater, container, false)
        val root: View = binding.root

      binding.btnBack.setOnClickListener {
      view?.findNavController()?.navigate(R.id.action_topWeeklyFragment_to_navigation_home)
      }
        return root
    }
}