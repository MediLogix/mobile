// MedicationsAdapter.kt
package hr.algebra.nasaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.model.Medication

class MedicationsAdapter(
    private val context: Context,
    private val medications: MutableList<Medication>,
    private val onEditClickListener: (Medication) -> Unit,
    private val onDeleteClickListener: (Medication) -> Unit
) : RecyclerView.Adapter<MedicationsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val editButton: MaterialButton = itemView.findViewById(R.id.editButton)
        private val deleteButton: MaterialButton = itemView.findViewById(R.id.deleteButton)

        fun bind(medication: Medication) {
            titleTextView.text = medication.name

            // Set click listener to open EditMedicationActivity
            editButton.setOnClickListener {
                onEditClickListener(medication)
            }

            // Set click listener to delete the medication
            deleteButton.setOnClickListener {
                onDeleteClickListener(medication)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.medication_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(medications[position])
    }

    override fun getItemCount() = medications.size
}
