package com.example.tenunaraapplication.main.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentHomeBinding
import com.example.tenunaraapplication.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private var token = ""
    private var _binding: FragmentHomeBinding? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var uid : String

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View? = binding?.root

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        userInfo()

        binding?.topweeklytenun?.setOnClickListener {
            goToTopWeekly()
        }

        binding?.scanhistory?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_historyScanActivity)
        }

        binding?.findtenunshop?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_findTenunShopMapsActivity)
        }

        binding?.scanwovenfabrics?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_scanWovenFabricActivity)
        }

        binding?.findtenuntraining?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_findTenunTrainingMapsActivity)
        }

        return root!!

    }

    private fun userInfo() {
        val userRef =
            FirebaseDatabase.getInstance().reference.child("USERS").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(Users::class.java)

                    binding?.username?.text = user!!.userName

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

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