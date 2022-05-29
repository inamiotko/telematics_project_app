package com.example.telematics_project.ui

import android.util.Log
import androidx.activity.addCallback
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


    private fun initialiseRecyclerView(patientList: List<Patient>) {
        binding.recyclerviewPatientList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = PatientListAdapter(patientList) { patient ->
                sharedViewModel.setPatientDetails(patient)
                viewModel.patientClicked()
            }
        }
    }

    override fun initViews() {
        overrideBackButton()
    }

    override fun initViewModel(viewModel: ListViewViewModel) {
        binding.viewModel = viewModel
        viewModel.start()
        viewModel.apply{
            patientClickedEvent.observe {
                findNavController().navigate(
                    ListViewFragmentDirections.actionListViewFragmentToPatientDetailsFragment()
                )
            }
            patients.observe {
                initialiseRecyclerView(it)
            }

        }
    }

    private fun overrideBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(ListViewFragmentDirections.actionListViewFragmentToMainViewFragment())
        }
    }
}