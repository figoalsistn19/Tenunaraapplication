package com.example.tenunaraapplication.main.ui.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.FragmentExploreBinding
import com.example.tenunaraapplication.main.ui.ListPostAdapter
import com.example.tenunaraapplication.main.ui.PostExplore


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val list = ArrayList<PostExplore>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ExploreViewModel =
            ViewModelProvider(this).get(ExploreViewModel::class.java)

        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvTenun.setHasFixedSize(true)

        list.addAll(listPost)
        showRecyclerList()
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val listPost: ArrayList<PostExplore>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listPost = ArrayList<PostExplore>()
            for (i in dataName.indices) {
                val post = PostExplore(dataName[i], dataPhoto.getResourceId(i, -1))
                listPost.add(post)
            }
            return listPost
        }
    private fun showRecyclerList() {
        val listPostAdapter = ListPostAdapter(list)
        binding.rvTenun.adapter = listPostAdapter
    }
}