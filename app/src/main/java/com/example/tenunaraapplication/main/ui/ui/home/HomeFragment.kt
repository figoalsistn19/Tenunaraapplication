package com.example.tenunaraapplication.main.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var token = ""
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.topweeklytenun.setOnClickListener {
            goToTopWeekly()
        }

        binding.scanhistory.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_historyScanActivity)
        }

        binding.findtenunshop.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_findTenunShopMapsActivity)
        }

        binding.scanwovenfabrics.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_cameraActivity)
        }

        return root

    }


    private fun goToTopWeekly() {
        val navigationAction = HomeFragmentDirections
            .actionNavigationHomeToTopWeeklyFragment()
        navigationAction.token = token

        findNavController().navigate(navigationAction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}