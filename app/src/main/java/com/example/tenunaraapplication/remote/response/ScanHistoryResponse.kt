package com.example.tenunaraapplication.remote.response

import com.google.gson.annotations.SerializedName

data class ScanHistoryResponse(

	@field:SerializedName("ScanHistoryResponse")
	val scanHistoryResponse: List<ScanHistoryResponseItem>
)

data class Tanggal(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)

data class ScanHistoryResponseItem(

	@field:SerializedName("Id_tenun")
	val idTenun: String,

	@field:SerializedName("Id_user")
	val idUser: String,

	@field:SerializedName("historyId")
	val historyId: String,

	@field:SerializedName("Bagikan")
	val bagikan: Boolean,

	@field:SerializedName("Nama_user")
	val namaUser: String,

	@field:SerializedName("Tanggal")
	val tanggal: Tanggal,

	@field:SerializedName("Foto_scan")
	val fotoScan: String
)
