package com.example.telematics_project.model

data class Patient(
    var id: String = "",
    var name: String = "",
    var age: Int = 0,
    var sex: String = "F",
    var symptoms: String = "",
    var conditions: String = "",
    var add_info: String = "",
    var imagePath: String = ""
) {
}