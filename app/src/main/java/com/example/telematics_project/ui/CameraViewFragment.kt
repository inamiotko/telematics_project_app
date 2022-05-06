package com.example.telematics_project.ui

import androidx.fragment.app.viewModels
import com.example.telematics_project.R
import com.example.telematics_project.base.BaseFragment
import com.example.telematics_project.databinding.FragmentCameraViewBinding
import com.example.telematics_project.viewmodel.CameraViewViewModel
import com.example.telematics_project.viewmodel.ListViewViewModel

class CameraViewFragment : BaseFragment<FragmentCameraViewBinding, CameraViewViewModel>() {

    override val viewModel: CameraViewViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_camera_view
    override fun initViews() {
    }

    override fun initViewModel(viewModel: CameraViewViewModel) {
        binding.viewModel = viewModel
    }

}