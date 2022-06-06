package com.example.tenunaraapplication.main.ui

sealed class UtilsData {
    data class Success<out T>(val data: T) : UtilsData()
    data class Error(val exception: Throwable) : UtilsData()
    data class Loading(val isLoading: Boolean) : UtilsData()
}
