package com.example.telematics_project.ui

import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentMainViewBinding


class MainViewFragment : BaseFragment<FragmentMainViewBinding>() {
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun getLayoutId(): Int = R.layout.fragment_main_view
    override fun initViews() {
        binding.addPatientImage.setOnClickListener {
            //todo add image capturing
        }
    }
}