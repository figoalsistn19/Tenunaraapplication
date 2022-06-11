@file:Suppress("DEPRECATION")

package com.example.tenunaraapplication.main.ui.ui.profile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentProfileBinding
import com.example.tenunaraapplication.login.ActivityLogin
import com.example.tenunaraapplication.model.Users
import java.io.ByteArrayOutputStream
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.widget.Toast
import coil.load
import com.example.tenunaraapplication.main.ui.MainActivity
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var imgUri : Uri
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
        val myRef = Firebase.database.reference
        val phoneNumber = binding.etPhone.text

        binding.btnEditProfile.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_navigation_profile_to_profileEditFragment)
        )

        binding.btnSetting.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_navigation_profile_to_settingFragment)
        )

        binding.btnLogout.setOnClickListener {
            btnLogout()
        }


        binding.profileImage.setOnClickListener {
            goToCamera()
        }

        binding.btnEditProfile.setOnClickListener {

            val phone = binding.etPhone.text.toString()

            if (phone.isEmpty()) {
                binding.etPhone.error = "PhoneNumber required"
                binding.etPhone.requestFocus()
                return@setOnClickListener
            }

            updatePhone(phone)


        }

        binding.btnChangePass.setOnClickListener {
            changePass()
        }

    }

    private fun getData(){
        val userRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user =snapshot.getValue(Users::class.java)
                    binding.tvNameProfile.text = user!!.userName
                    binding.profileImage.load(firebaseUser.photoUrl)
                    binding.tvUsername.setText(user.userName)
                    binding.etPhone.setText(user.phoneNumber)
                    binding.etEmail.setText(firebaseUser.email)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun updatePhone(phoneNumber: String) {
        ref = FirebaseDatabase.getInstance().getReference("USERS")
        val user = mapOf(
            "phoneNumber" to phoneNumber
        )

        ref.child(phoneNumber).updateChildren(user).addOnSuccessListener {

            binding.etPhone.text.clear()
            saveData()

        }.addOnFailureListener{
            Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()

        }
    }

    private fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    this.startActivityForResult(intent, REQ_CAM)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAM && resultCode == RESULT_OK) {
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
            .addOnCompleteListener{ it ->
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener {
                        it.result.let{
                            imgUri = it
                            binding.profileImage.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    private fun saveData () {
        val user = auth.currentUser

        if (user != null){
            if (user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(binding.profileImage)
            } else{
                Picasso.get().load("https://picsum.photos/seed/picsum/200/300").into(binding.profileImage)
            }
        }
        val image = when{
            ::imgUri.isInitialized -> imgUri
            user?.photoUrl == null -> Uri.parse("https://picsum.photos/seed/picsum/200/300")
            else -> user.photoUrl
        }

        UserProfileChangeRequest.Builder()
            .setPhotoUri(image)
            .build().also { it ->
                val progressDialog = ProgressDialog(activity)
                progressDialog.setTitle("Registratation User")
                progressDialog.setMessage("Please Wait")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                user?.updateProfile(it)?.addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(activity, "Register is Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }else{
                        Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


    }

    private fun changePass() {
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.cvCurrentPass.visibility = View.VISIBLE
        binding.cvUser.visibility = View.INVISIBLE
        binding.icUser.visibility = View.INVISIBLE
        binding.cvPhone.visibility = View.INVISIBLE
        binding.icPhone.visibility = View.INVISIBLE
        binding.cvEmail.visibility = View.INVISIBLE
        binding.icEmail.visibility = View.INVISIBLE

        binding.btnCancel.setOnClickListener {
            binding.cvCurrentPass.visibility = View.GONE
            binding.cvUser.visibility = View.VISIBLE
            binding.icUser.visibility = View.VISIBLE
            binding.cvPhone.visibility = View.VISIBLE
            binding.icPhone.visibility = View.VISIBLE
            binding.cvEmail.visibility = View.VISIBLE
            binding.icEmail.visibility = View.VISIBLE
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

    companion object{
        const val REQ_CAM = 100
    }
}


