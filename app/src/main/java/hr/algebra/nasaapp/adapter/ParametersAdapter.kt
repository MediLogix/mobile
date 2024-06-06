package hr.algebra.nasaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.model.Parameter

class ParametersAdapter(
    private val context: Context,
    private val parameters: List<Parameter>
) : RecyclerView.Adapter<ParametersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvParameterName)
        private val unitTextView: TextView = itemView.findViewById(R.id.tvParameterUnit)

        fun bind(parameter: Parameter) {
            nameTextView.text = parameter.name
            unitTextView.text = parameter.unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.parameter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(parameters[position])
    }

    override fun getItemCount() = parameters.size
}
