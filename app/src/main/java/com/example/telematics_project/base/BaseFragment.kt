package com.example.telematics_project.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telematics_project.event.Event
import com.example.telematics_project.event.EventObserver
import com.example.telematics_project.viewmodel.SharedViewModel

abstract class BaseFragment<VDB : ViewDataBinding, VM: ViewModel> : Fragment() {
    protected lateinit var binding: VDB
    protected val sharedViewModel: SharedViewModel by activityViewModels()
    protected abstract val viewModel: VM

    abstract fun getLayoutId(): Int

    abstract fun initViewModel(viewModel: VM)

    abstract fun initViews()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel(viewModel)
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onStart() {
        initViews()
        super.onStart()
    }

    fun <T> LiveData<T>.observe(observe: ((value: T) -> Unit)) {
        this.observe(viewLifecycleOwner) { value -> observe(value) }
    }

    fun <T> LiveData<Event<T>>.observeEvent(observe: ((value: T) -> Unit)) {
        this.observe(viewLifecycleOwner, EventObserver { value -> observe(value) })
    }
}