package com.example.megarideapplication.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.megarideapplication.Adapter.AvailableAdapter
import com.example.megarideapplication.Adapter.HistoryAdapter
import com.example.megarideapplication.Models.AvailableModel
import com.example.megarideapplication.Models.HistoryModel
import com.example.megarideapplication.R


class Availableridesfragment : Fragment() {
    private lateinit var availableAdapter: AvailableAdapter
    private val availableModelClassdataList = ArrayList<AvailableModel>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val view= inflater.inflate(R.layout.fragment_availableridesfragment, container, false)
        initilisation(view)

        return view
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initilisation(view: View) {
        recyclerView= view.findViewById(R.id.availablrrecyclerview)
        availableAdapter= AvailableAdapter(requireActivity().applicationContext,availableModelClassdataList)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = availableAdapter
        this.availableModelClassdataList.clear()
        var available = AvailableModel("8 JUNE 2021,","18:39","From","To","1,Thrale Street, London, SE!9HW,UK",
            "Ealing Broadway Shopping Center,london,w55jy,UK" ,R.drawable.available)
        availableModelClassdataList.add(available)

        available = AvailableModel("8 JUNE 2021,","18:39","From","To","1,Thrale Street, London, SE!9HW,UK",
            "Ealing Broadway Shopping Center,london,w55jy,UK" ,R.drawable.available)
        availableModelClassdataList.add(available)

        availableAdapter.notifyDataSetChanged()


    }

}