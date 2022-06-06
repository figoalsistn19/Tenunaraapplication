package com.example.tenunaraapplication.remote.api

import com.example.tenunaraapplication.remote.response.ScanHistoryResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("scanHistory")
    fun getHistoryScan(
        @Query("per_page") page: Int? = null,
    ): List<ScanHistoryResponseItem>
}