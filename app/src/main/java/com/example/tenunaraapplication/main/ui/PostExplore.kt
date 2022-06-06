package com.example.tenunaraapplication.main.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostExplore(
    var name: String,
    var photo: Int
) :Parcelable