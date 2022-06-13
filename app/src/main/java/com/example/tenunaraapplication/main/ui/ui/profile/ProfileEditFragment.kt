package com.example.tenunaraapplication.main.ui.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentProfileEditBinding
import com.example.tenunaraapplication.login.ActivityLogin
import com.example.tenunaraapplication.model.Users
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ProfileEditFragment : Fragment() {

    private var token = ""
    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var imgUri : Uri
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        ref = FirebaseDatabase.getInstance().getReference("USERS")

        getData()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnBack.setOnClickListener {
            backToProfile()
        }

        binding.ivEditProfile.setOnClickListener {
            goToCamera()
        }

        binding.btnEditProfile.setOnClickListener {

            val phone = binding.etPhone.text.toString()

            updatePhone(phone)

        }

        binding.btnChangePass.setOnClickListener {
            changePass()
        }

    }

    private fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    this.startActivityForResult(intent, ProfileEditFragment.REQ_CAM)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ProfileEditFragment.REQ_CAM && resultCode == Activity.RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImgToFirebase(imgBitmap)
        }
    }

    private fun uploadImgToFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val img = baos.toByteArray()
        ref.putBytes(img)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener { Task ->
                        Task.result.let{ Uri ->
                            imgUri =Uri
                            binding.ivEditProfile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    private fun updatePhone( phoneNumber: String) {
        ref = FirebaseDatabase.getInstance().getReference("USERS")
        val user = mapOf(
            "phoneNumber" to phoneNumber
        )

        ref.child(firebaseUser.uid).updateChildren(user).addOnSuccessListener {
            saveData()

        }.addOnFailureListener{
            Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()

        }
    }

    private fun saveData () {
        val user = auth.currentUser

        if (user != null){
            if (user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(binding.ivEditProfile)
            } else{
                Picasso.get().load("https://picsum.photos/seed/picsum/200/300").into(binding.ivEditProfile)
            }
        }
        val image = when{
            ::imgUri.isInitialized -> imgUri
            user?.photoUrl == null -> Uri.parse("https://picsum.photos/seed/picsum/200/300")
            else -> user.photoUrl
        }

        UserProfileChangeRequest.Builder()
            .setPhotoUri(image)
            .build().also {
                user?.updateProfile(it)?.addOnCompleteListener { Task ->
                    if (Task.isSuccessful){
                        Toast.makeText(activity, getString(R.string.succes_edt), Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            ProfileEditFragmentDirections.actionProfileEditFragmentToNavigationProfile()
                        )
                    }else{
                        Toast.makeText(activity, "${Task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun getData(){
        val userRef = FirebaseDatabase.getInstance().reference.child("USERS").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user =snapshot.getValue(Users::class.java)

                    binding.ivEditProfile.load(firebaseUser.photoUrl)
                    binding.usernameEditText.setText(user!!.userName)
                    binding.etPhone.setText(user.phoneNumber)
                    binding.etEmail.setText(firebaseUser.email)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun changePass() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.cvCurrentPass.visibility = View.VISIBLE

        binding.btnCancel.setOnClickListener {
        }

        binding.btnConfirm.setOnClickListener btnConfirm@{

            val pass = binding.edtCurrentPassword.text.toString()

            if (pass.isEmpty()) {
                binding.edtCurrentPassword.error = "Password Tidak Boleh Kosong"
                binding.edtCurrentPassword.requestFocus()
                return@btnConfirm
            }

            user.let {
                val userCredential = EmailAuthProvider.getCredential(it?.email!!,pass)
                it.reauthenticate(userCredential).addOnCompleteListener { task ->
                    when {
                        task.isSuccessful -> {
                            binding.cvCurrentPass.visibility = View.GONE
                            binding.cvUpdatePass.visibility = View.VISIBLE

                        }
                        task.exception is FirebaseAuthInvalidCredentialsException -> {
                            binding.edtCurrentPassword.error = "Password Salah"
                            binding.edtCurrentPassword.requestFocus()
                        }
                        else -> {
                            Toast.makeText(activity, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            binding.btnNewCancel.setOnClickListener {
                binding.cvCurrentPass.visibility = View.GONE
                binding.cvUpdatePass.visibility = View.GONE
            }

            binding.btnNewChange.setOnClickListener newChangePassword@{

                val newPass = binding.edtNewPass.text.toString()
                val passConfirm = binding.edtConfirmPass.text.toString()

                if (newPass.isEmpty()) {
                    binding.edtCurrentPassword.error = "Password Tidak Boleh Kosong"
                    binding.edtCurrentPassword.requestFocus()
                    return@newChangePassword
                }

                if(passConfirm.isEmpty()){
                    binding.edtCurrentPassword.error = "Ulangi Password Baru"
                    binding.edtCurrentPassword.requestFocus()
                    return@newChangePassword
                }

                if (newPass.length < 6) {
                    binding.edtCurrentPassword.error = "Password Harus Lebih dari 6 Karakter"
                    binding.edtCurrentPassword.requestFocus()
                    return@newChangePassword
                }

                if (passConfirm.length < 6) {
                    binding.edtCurrentPassword.error = "Password Tidak Sama"
                    binding.edtCurrentPassword.requestFocus()
                    return@newChangePassword
                }

                if (newPass != passConfirm) {
                    binding.edtCurrentPassword.error = "Password Tidak Sama"
                    binding.edtCurrentPassword.requestFocus()
                    return@newChangePassword
                }

                user?.let {
                    user.updatePassword(newPass).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(activity, "Password Berhasil diUpdate", Toast.LENGTH_SHORT).show()
                            successLogout()
                        } else {
                            Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }

    private fun successLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()

        val intent = Intent(context, ActivityLogin::class.java)
        startActivity(intent)
        activity?.finish()

        Toast.makeText(activity, "Silahkan Login Kembali", Toast.LENGTH_SHORT).show()
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

    companion object{
        const val REQ_CAM = 100
    }
}