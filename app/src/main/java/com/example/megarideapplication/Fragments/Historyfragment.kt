package com.example.megarideapplication.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.megarideapplication.Adapter.HistoryAdapter
import com.example.megarideapplication.Models.HistoryModel
import com.example.megarideapplication.R


class Historyfragment : Fragment() {
    private lateinit var historyAdapter: HistoryAdapter
    private val historyModelClassdataList = ArrayList<HistoryModel>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val view= inflater.inflate(R.layout.fragment_historyfragment, container, false)
        initilisation(view)

        return view
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initilisation(view: View) {
        recyclerView  = view.findViewById(R.id.historyrecyclerview)
        historyAdapter= HistoryAdapter(requireActivity().applicationContext,historyModelClassdataList)



        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = historyAdapter
        this.historyModelClassdataList.clear()
        var history = HistoryModel("Skndr Bkht","05 AUG,2020 at 10:00 am","Block N,East london","Block A,london",
            R.drawable.tracelocation)
        historyModelClassdataList.add(history)

        history = HistoryModel("Skndr Bkht","05 AUG,2020 at 10:00 am","Block N,East london","Block A,london",
            R.drawable.tracelocation)
        historyModelClassdataList.add(history)

        history = HistoryModel("Skndr Bkht","05 AUG,2020 at 10:00 am","Block N,East london","Block A,london",
            R.drawable.tracelocation)
        historyModelClassdataList.add(history)

        historyAdapter.notifyDataSetChanged()

    }

}