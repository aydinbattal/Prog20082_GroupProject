package com.example.prog20082_groupproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Prog20082_GroupProject created by aydin
 * student ID : 991521740
 * on 08/12/20 */

class ReceiptAdapter(
    val context: Context,
    val receiptsList: MutableList<Booking>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReceiptAdapter.ReceiptViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.booking_receipt, null)
        return ReceiptViewHolder(view)
    }

    override fun getItemCount(): Int {
        return receiptsList.size
    }

    override fun onBindViewHolder(holder: ReceiptAdapter.ReceiptViewHolder, position: Int) {
        holder.bind(receiptsList[position], itemClickListener)
    }

    inner class ReceiptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvDateBooked: TextView = itemView.findViewById(R.id.tvDateBooked)
        var tvStudentIdBooked: TextView = itemView.findViewById(R.id.tvStudentIdBooked)
        var tvStudentAmtBooked: TextView = itemView.findViewById(R.id.tvStudentAmtBooked)
        var tvLocationBooked: TextView = itemView.findViewById(R.id.tvLocationBooked)

        fun bind(receipt: Booking, clickListener: OnItemClickListener){
            tvDateBooked.setText(receipt.parkingDate.toString())
            tvStudentIdBooked.setText(receipt.buildingCode.toString())
            tvStudentAmtBooked.setText(receipt.unitNumber.toString())
            tvLocationBooked.setText(receipt.duration.toString())

            itemView.setOnClickListener{
                clickListener.onItemClicked(receipt)
            }
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(receipt: Booking)
}