package com.example.tenunaraapplication.model

import com.google.firebase.database.Exclude

data class Users(
    val id: String,
    val email: String,
    val userName: String?,
    val phoneNumber: String?
) {
    constructor():this("","","","")

}