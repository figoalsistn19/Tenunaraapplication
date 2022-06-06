package com.example.tenunaraapplication.main.ui.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.remote.ScanHistory

class HistoryScanAdapter(private val scanHistoryList: ArrayList<ScanHistory>) : RecyclerView.Adapter<HistoryScanAdapter.HistoryScanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryScanAdapter.HistoryScanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)

        return HistoryScanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryScanAdapter.HistoryScanViewHolder, position: Int) {

        val scanHistory : ScanHistory = scanHistoryList[position]
        holder.tvDesc.text = scanHistory.Id_tenun
        holder.tvName.text = scanHistory.Nama_user
    }

    override fun getItemCount(): Int {
        return scanHistoryList.size
    }

    inner class HistoryScanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        val tvName: TextView = itemView.findViewById(R.id.tv_date)
        val tvDate: TextView = itemView.findViewById(R.id.tv_time)

    }
}