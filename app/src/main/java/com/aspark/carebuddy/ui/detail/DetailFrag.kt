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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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


        val calendar = Calendar.getInstance()
        var dayMonth = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)

        binding.tvDate.setOnClickListener {

            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                    Log.d("DetailFrag", "onViewCreated: Now date: " +
                            "$selectedDayOfMonth $selectedMonth $selectedYear")

                    binding.tvDate.text = formatDate(selectedDayOfMonth,
                        selectedMonth+1, selectedYear)

                    dayMonth = selectedDayOfMonth
                    month = selectedMonth
                    year = selectedYear

                }, year, month, dayMonth)

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()

        }
    }

    private fun formatDate(day: Int, month: Int, year: Int): String {


        var sDay: String = day.toString()
        var sMonth: String = month.toString()

        if(sDay.length == 1)
            sDay = "0$sDay"

        if (sMonth.length == 1)
            sMonth = "0$sMonth"

        val date = "$sDay $sMonth, $year"

        val inputFormat = DateTimeFormatter.ofPattern("dd MM, yyyy")
        val outputFormat = DateTimeFormatter.ofPattern("dd MMM, yyyy")
        val parsedDate = LocalDate.parse(date, inputFormat)
        return outputFormat.format(parsedDate)
    }

}
