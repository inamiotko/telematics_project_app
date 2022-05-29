package com.example.telematics_project.ui

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.telematics_project.R
import com.example.telematics_project.TelematicsProjectApplication.Companion.context
import com.example.telematics_project.model.Patient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage


class PatientListAdapter(private val dataSet: List<Patient>, private val clickListener: (patient: Patient) -> Unit?) :
    RecyclerView.Adapter<PatientListAdapter.ViewHolder>() {



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var patientNameTV: TextView? = null
        var patientAgeTV: TextView? = null
        var patientSexTV: TextView? = null
        var patientImageView: ImageView? = null



        init {
            patientNameTV = itemView.findViewById(R.id.patient_name)
            patientAgeTV = itemView.findViewById(R.id.patient_age)
            patientSexTV = itemView.findViewById(R.id.patient_sex)
            patientImageView = itemView.findViewById(R.id.patient_image)
        }

        fun bind(patient: Patient) {
            Log.i("###", patient.imagePath)
            Log.i("###", patient.name)

            val storage = FirebaseStorage.getInstance()
            val ref = storage.getReferenceFromUrl(patient.imagePath)
            ref.downloadUrl.addOnSuccessListener(
                OnSuccessListener<Uri> { uri ->
                    patientImageView?.let {
                        Glide.with(context)
                            .load(uri.toString()).into(it)
                    }
                }).addOnFailureListener(
                OnFailureListener {
                    // Handle any errors
                })
            patientNameTV?.text = String.format(patient.name)
            patientSexTV?.text = String.format(context.resources.getString(R.string.sex),patient.sex)
            patientAgeTV?.text = String.format(context.resources.getString(R.string.age),patient.age)
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
        holder.itemView.setOnClickListener {
            clickListener.invoke(dataSet[position])
        }
    }

    override fun getItemCount(): Int = dataSet.size
}