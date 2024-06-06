package hr.algebra.nasaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.model.Measurement
import hr.algebra.nasaapp.model.Parameter

class MeasurementsAdapter(
    private val context: Context,
    private val measurements: MutableList<Measurement>,
    private val onEditClickListener: (Measurement) -> Unit,
    private val onDeleteClickListener: (Measurement) -> Unit
) : RecyclerView.Adapter<MeasurementsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvMeasurementName)
        private val editButton: MaterialButton = itemView.findViewById(R.id.editButton)
        private val deleteButton: MaterialButton = itemView.findViewById(R.id.deleteButton)
        private val parametersRecyclerView: RecyclerView = itemView.findViewById(R.id.rvParameters)

        fun bind(measurement: Measurement) {
            nameTextView.text = measurement.name

            // Set up the parameters RecyclerView
            parametersRecyclerView.layoutManager = LinearLayoutManager(context)
            parametersRecyclerView.adapter = ParametersAdapter(context, measurement.parameters)

            // Set click listener to open EditMeasurementActivity
            editButton.setOnClickListener {
                onEditClickListener(measurement)
            }

            // Set click listener to delete the measurement
            deleteButton.setOnClickListener {
                onDeleteClickListener(measurement)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.measurement_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measurement = measurements[position]
        holder.bind(measurement)
        holder.itemView.findViewById<Button>(R.id.editButton).setOnClickListener {
            onEditClickListener(measurement) // Pass the measurement ID to the click listener
        }
        holder.itemView.findViewById<Button>(R.id.deleteButton).setOnClickListener {
            onDeleteClickListener(measurement)
        }
    }
    override fun getItemCount() = measurements.size
}
