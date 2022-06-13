package com.example.tenunaraapplication.main.ui.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentProfileBinding
import com.example.tenunaraapplication.login.ActivityLogin
import com.example.tenunaraapplication.model.Users
import coil.load
import com.google.firebase.auth.*
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        ref = FirebaseDatabase.getInstance().getReference("USERS")

        getData()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnEditProfile.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_navigation_profile_to_profileEditFragment)
        )

        binding.btnSetting.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_navigation_profile_to_settingFragment)
        )

        binding.btnLogout.setOnClickListener {
            btnLogout()
        }


        }


    private fun getData(){
        val userRef = FirebaseDatabase.getInstance().reference.child("USERS").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user =snapshot.getValue(Users::class.java)
                    binding.tvNameProfile.text = user!!.userName
                    binding.profileImage.load(firebaseUser.photoUrl)
                    binding.tvUsername.text = user.userName
                    binding.etPhone.text = user.phoneNumber
                    binding.etEmail.text = firebaseUser.email

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    private fun btnLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(context,ActivityLogin::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

