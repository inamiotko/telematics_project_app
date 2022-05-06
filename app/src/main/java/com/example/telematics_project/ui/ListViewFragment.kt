package com.example.telematics_project.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentListViewBinding
import com.example.telematics_project.model.Patient
import com.example.telematics_project.viewmodel.ListViewViewModel

class ListViewFragment() : BaseFragment<FragmentListViewBinding, ListViewViewModel>() {

    override val viewModel: ListViewViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_list_view

    private val patientList = listOf(
        Patient("jan kowalski", "16", "F", "blablabla", "blabla", "symptomsss", "imagepath"),
        Patient("jan kowalski 2", "26", "M", "blablabla", "blabla", "symptomsss", "imagepath2"),
        Patient( "jan kowalski 3", "90", "F", "blablabla", "blabla", "symptomsss", "imagepath3")
    )

    private fun initialiseRecyclerView() {
        binding.recyclerviewPatientList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PatientListAdapter(patientList) { patient ->
                sharedViewModel.setPatientDetails(patient)
                viewModel.patientClicked()
            }
        }
    }

    override fun initViews() {
        initialiseRecyclerView()
    }

    override fun initViewModel(viewModel: ListViewViewModel) {
        binding.viewModel = viewModel
        viewModel.apply{
            patientClickedEvent.observe {
                findNavController().navigate(
                    ListViewFragmentDirections.actionListViewFragmentToPatientDetailsFragment()
                )
            }

        }
    }
}