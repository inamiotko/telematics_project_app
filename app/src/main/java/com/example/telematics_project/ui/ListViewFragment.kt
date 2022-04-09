package com.example.telematics_project.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentListViewBinding
import com.example.telematics_project.model.Patient

class ListViewFragment : BaseFragment<FragmentListViewBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_list_view

    private val patientList = listOf(
        Patient("1", "jan kowalski", "blabla", "imagepath"),
        Patient("2", "jan kowalski 2", "blabla", "imagepath2"),
        Patient("3", "jan kowalski 3", "blabla", "imagepath3")
    )

    private fun initialiseRecyclerView() {
        binding.recyclerviewPatientList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PatientListAdapter(patientList)
        }
    }

    override fun initViews() {
        initialiseRecyclerView()
    }
}