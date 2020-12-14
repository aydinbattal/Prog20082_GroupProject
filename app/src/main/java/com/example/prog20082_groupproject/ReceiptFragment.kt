package com.example.prog20082_groupproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prog20082_groupproject.database.Booking
import com.example.prog20082_groupproject.database.BookingViewModel

class ReceiptFragment : Fragment(), OnItemClickListener {
    private lateinit var bookingViewModel : BookingViewModel

    private lateinit var rvReceipts : RecyclerView
    private lateinit var viewAdapter: ReceiptAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var receiptsList: MutableList<Booking>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_receipt, container, false)

        this.rvReceipts = root.findViewById(R.id.rvReceipts)
        this.receiptsList = mutableListOf()

        this.viewAdapter = ReceiptAdapter(this.requireContext(), this.receiptsList, this)
        this.rvReceipts.adapter = this.viewAdapter

        this.viewManager = LinearLayoutManager(this.requireContext())
        this.rvReceipts.layoutManager = this.viewManager

        this.rvReceipts.setHasFixedSize(true)
        this.rvReceipts.addItemDecoration(DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL))

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookingViewModel = BookingViewModel(this.requireActivity().application)
    }

    override fun onResume() {
        super.onResume()

        bookingViewModel.allBooking
        this.getReceiptList()
    }

    fun getReceiptList(){
        this.bookingViewModel.allBooking.observe(viewLifecycleOwner, {bookingList ->
            if (bookingList != null){
                receiptsList.clear()
                receiptsList.addAll(bookingList)
                viewAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onItemClicked(receipt: Booking) {
        TODO("Not yet implemented")
    }

}