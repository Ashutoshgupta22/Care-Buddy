package com.aspark.carebuddy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.FragmentHomeBinding

class HomeFrag: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //shows search view when search bar is clicked
        binding.searchView.setupWithSearchBar(binding.searchBar)

        binding.viewPagerUpcoming.adapter = UpcomingBookingAdapter(arrayListOf())
        binding.indicatorWormDots.attachTo(binding.viewPagerUpcoming)

        binding.rvTopNurse.apply {
            layoutManager = LinearLayoutManager(this@HomeFrag.requireContext(),
                RecyclerView.HORIZONTAL, false)

            adapter = TopNurseAdapter(arrayListOf())

        }

        binding.rvSelfCare.apply {
            layoutManager = LinearLayoutManager(this@HomeFrag.requireContext(),
            RecyclerView.HORIZONTAL, false)

            adapter = SelfCareAdapter(arrayListOf())
        }

    }
}