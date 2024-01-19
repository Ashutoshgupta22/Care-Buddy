package com.aspark.carebuddy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.carebuddy.R
import com.aspark.carebuddy.databinding.ItemUpcomingBookingBinding
import com.aspark.carebuddy.model.BookingModel

class UpcomingBookingAdapter( private val bookingList: ArrayList<BookingModel>):
    RecyclerView.Adapter<UpcomingBookingAdapter.Holder>() {

    class Holder(binding: ItemUpcomingBookingBinding) : RecyclerView.ViewHolder(binding.root){

        val nursePic = binding.ivUpcomingNursePic
        val nurseName = binding.tvUpcomingNurseName
        val nurseServices = binding.tvUpcomingNurseServices
        val appointmentDate = binding.tvAppointmentDate
        val appointmentTime = binding.tvAppointmentTime

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUpcomingBookingBinding.inflate(layoutInflater,
            parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {

        return 3
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.nurseName.text = "Joker"
        holder.nurseServices.text = "Extra Care"
        holder.nursePic.setImageResource(R.drawable.ic_launcher_foreground)
        holder.nursePic.setBackgroundResource(R.drawable.ic_launcher_background)

    }
}