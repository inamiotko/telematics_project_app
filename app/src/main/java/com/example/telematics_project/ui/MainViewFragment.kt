package com.example.telematics_project.ui

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentMainViewBinding
import com.example.telematics_project.viewmodel.SharedViewModel


class MainViewFragment : BaseFragment<FragmentMainViewBinding, SharedViewModel>(),
    AdapterView.OnItemSelectedListener {
    override val viewModel: SharedViewModel by viewModels()
    private val REQUEST_IMAGE_CAPTURE = 1
    val patientAgeList: List<Int> = (1..100).toList()
    override fun getLayoutId(): Int = R.layout.fragment_main_view
    override fun initViews() {
        binding.addPatientAge.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            patientAgeList
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.addPatientAge.adapter = adapter
        }
        binding.addPatientImage.setOnClickListener {
            //todo add image capturing
        }
    }

    override fun initViewModel(viewModel: SharedViewModel) {
        //
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}