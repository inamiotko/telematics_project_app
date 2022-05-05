package com.example.telematics_project.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telematics_project.R
import com.example.telematics_project.model.Patient

class PatientListAdapter(private val dataSet: List<Patient>) :
    RecyclerView.Adapter<PatientListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var patientNameTV: TextView? = null
        var patientAgeTV: TextView? = null
        var patientSexTV: TextView? = null
        var patientConditions: TextView? = null
        var patientSymptomsTV: TextView? = null
        var patientAddInfoTV: TextView? = null
        var patientImageIV: ImageView? = null

        init {
            patientNameTV = itemView.findViewById(R.id.patient_name)
            patientAgeTV = itemView.findViewById(R.id.patient_age)
            patientSexTV = itemView.findViewById(R.id.patient_sex)
            patientConditions = itemView.findViewById(R.id.patient_conditions)
            patientSymptomsTV = itemView.findViewById(R.id.patient_symptoms)
            patientAddInfoTV = itemView.findViewById(R.id.patient_add_info)
            patientImageIV = itemView.findViewById(R.id.patient_image)
        }

        fun bind(patient: Patient) {
            patientNameTV?.text = patient.name
            patientSexTV?.text = patient.sex
            patientAgeTV?.text = patient.age.toString()
            patientConditions?.text = patient.conditions
            patientSymptomsTV?.text = patient.symptoms
            patientAddInfoTV?.text = patient.add_info
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_patient_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient: Patient = dataSet[position]
        holder.bind(patient)
//        holder.itemView.setOnClickListener {
//            clickListener.invoke(list[position])
//        }
    }

    override fun getItemCount(): Int = dataSet.size
}