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
        var patientDescriptionTV: TextView? = null
        var patientImageIV: ImageView? = null

        init {
            patientNameTV = itemView.findViewById(R.id.patient_name)
            patientDescriptionTV = itemView.findViewById(R.id.patient_details)
            patientImageIV = itemView.findViewById(R.id.patient_image)
        }

        fun bind(patient: Patient) {
            patientNameTV?.text = patient.name
            patientDescriptionTV?.text = patient.description
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