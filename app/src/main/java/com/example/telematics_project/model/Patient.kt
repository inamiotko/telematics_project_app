package com.example.telematics_project.model

data class Patient(
    var name: String = "",
    var age: String = "",
    var sex: String = "F",
    var symptoms: String = "",
    var conditions: String = "",
    var add_info: String = "",
    var imagePath: String = ""
) {
}