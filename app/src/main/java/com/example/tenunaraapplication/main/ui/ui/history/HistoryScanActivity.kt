package com.example.tenunaraapplication.main.ui.ui.history

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.ActivityHistoryScanBinding
import com.example.tenunaraapplication.main.ui.ui.home.HomeFragment
import com.example.tenunaraapplication.remote.ScanHistory
import com.google.firebase.firestore.*

class HistoryScanActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scanHistoryArrayList: ArrayList<ScanHistory>
    private lateinit var historyScanAdapter: HistoryScanAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityHistoryScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_scan)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        scanHistoryArrayList = arrayListOf()

        historyScanAdapter = HistoryScanAdapter(scanHistoryArrayList)

        recyclerView.adapter = historyScanAdapter

        eventChangeListener()

        binding.btnBack.setOnClickListener {
            val intent = Intent(this@HistoryScanActivity,HomeFragment::class.java)
            startActivity(intent)
        }

    }

    private fun eventChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("Scan_history").addSnapshotListener(object : EventListener<QuerySnapshot> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("Firestore Error",error.message.toString() )
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        scanHistoryArrayList.add(dc.document.toObject(ScanHistory::class.java))
                    }
                }

                historyScanAdapter.notifyDataSetChanged()
            }

        })
    }
}