package com.aspark.carebuddy.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.databinding.FragmentHomeBinding
import com.aspark.carebuddy.model.User.Companion.currentUser
import com.aspark.carebuddy.ui.SecondActivity

class HomeFrag: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()

        Log.d("HomeFrag", "onViewCreated: called ")

        //shows search view when search bar is clicked
        binding.searchView.setupWithSearchBar(binding.searchBar)

        binding.viewPagerUpcoming.adapter = UpcomingBookingAdapter(arrayListOf())
        binding.indicatorWormDots.attachTo(binding.viewPagerUpcoming)

        binding.rvTopNurse.apply {
            layoutManager = LinearLayoutManager(this@HomeFrag.requireContext(),
                RecyclerView.HORIZONTAL, false)

          //  adapter = TopNurseAdapter(arrayListOf())

        }

        binding.rvSelfCare.apply {
            layoutManager = LinearLayoutManager(this@HomeFrag.requireContext(),
            RecyclerView.HORIZONTAL, false)

            adapter = SelfCareAdapter(arrayListOf())
        }

        //TODO learn more about viewLifecycleOwner
        viewModel.getUserdata.observe(viewLifecycleOwner) {

            it?.let {
                if (it) {
                    Log.d("HomeFrag", "onViewCreated: updating user data ui")
                    Log.i("HomeFrag", "onViewCreated: currentUser: $currentUser")

                    binding.tvName.text = currentUser.firstName
                }
            }
        }

        viewModel.topNurseList.observe(viewLifecycleOwner) {

            it?.let {
                binding.rvTopNurse.adapter = TopNurseAdapter(it) {

//                    val action = HomeFragDirections.actionHomeFragToSecondActivity(it)
//                    navController.navigate(action)

                    Log.i("HomeFrag", "onViewCreated: onItemClick callback ")
                    val intent = Intent(requireContext(), SecondActivity::class.java)
                    intent.putExtra("nurseId",it)
                    startActivity(intent)

                }
            }
        }

    }
}