package com.aspark.carebuddy.ui.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.FragmentDetailBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.util.Calendar

class DetailFrag : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = view.findNavController()
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFrag))
        binding.toolbarDetailFrag.setupWithNavController(navController, appBarConfiguration)

        binding.rvSpecialitiesNurse.apply {

            val flexLayout = FlexboxLayoutManager(this@DetailFrag.requireContext(),
                FlexDirection.ROW, FlexWrap.WRAP)
            flexLayout.justifyContent = JustifyContent.SPACE_EVENLY

            layoutManager = flexLayout
            adapter = SpecialitiesAdapter(arrayListOf("Post Surgery", "Baby care", "Elder care"))
        }

        binding.rvTimeDetail.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = TimeDetailAdapter(arrayListOf())
        }

        binding.tvDate.setOnClickListener {

            val calendar = Calendar.getInstance()
            val dayMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            Log.d("DetailFrag", "onViewCreated: Now date: $dayMonth $month $year")


            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                    Log.d("DetailFrag", "onViewCreated: Now date: " +
                            "$selectedDayOfMonth $selectedMonth $selectedYear")


                }, year, month, dayMonth)

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()

        }
    }

}
