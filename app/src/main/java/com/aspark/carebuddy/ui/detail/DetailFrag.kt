package com.aspark.carebuddy.ui.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.FragmentDetailBinding
import com.aspark.carebuddy.model.Nurse
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@AndroidEntryPoint
class DetailFrag : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var nurseFirebaseToken: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val nurseId = activity?.intent?.getIntExtra("nurseId",-1)!!

        nurseId?.let {

            if (nurseId == -1 )
                Toast.makeText(requireContext(), "Couldn't load data",
                    Toast.LENGTH_SHORT).show()

            else viewModel.getNurseById(it)
        }

        viewModel.getNurse.observe(viewLifecycleOwner) {

            it?.let {
                setUi(it)
                nurseFirebaseToken = it.firebaseToken
            }
        }

        binding.btnBookAppointment.isEnabled = false

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

                    binding.btnBookAppointment.isEnabled = true

                    dayMonth = selectedDayOfMonth
                    month = selectedMonth
                    year = selectedYear

                }, year, month, dayMonth)

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()

        }

        binding.btnBookAppointment.setOnClickListener {

            if (nurseFirebaseToken.isNotEmpty() && nurseId != -1) {
                viewModel.bookAppointment(nurseId, nurseFirebaseToken)
            }

        }
    }

    private fun setUi(it: Nurse) {

        binding.tvDetailName.text = "${it.firstName} ${it.lastName}"
        binding.tvRating.text = it.rating.toString()
        binding.tvBiographyNurse.text = it.biography

        binding.rvSpecialitiesNurse.apply {

            val flexLayout = FlexboxLayoutManager(this@DetailFrag.requireContext(),
                FlexDirection.ROW, FlexWrap.WRAP)
            flexLayout.justifyContent = JustifyContent.SPACE_EVENLY

            layoutManager = flexLayout
            adapter = SpecialitiesAdapter(it.specialities)
        }

        binding.rvTimeDetail.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = TimeDetailAdapter(arrayListOf())
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
