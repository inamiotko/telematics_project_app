package com.example.telematics_project.base

import androidx.navigation.ui.AppBarConfiguration
import com.example.telematics_project.R

class BottomBarConfiguration {
    companion object {
        val configuration = AppBarConfiguration(
            setOf(
                R.id.mainViewFragment,
                R.id.cameraViewFragment,
                R.id.listViewFragment
            )
        )
    }
}