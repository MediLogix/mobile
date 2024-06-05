package hr.algebra.nasaapp.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.nasaapp.ItemPagerActivity
import hr.algebra.nasaapp.NASA_PROVIDER_CONTENT_URI
import hr.algebra.nasaapp.POSITION
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.framework.startActivity
import hr.algebra.nasaapp.model.Item
import hr.algebra.nasaapp.model.Medication
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class MedicationPagerAdapter(
    private val context: Context,
    private val items: MutableList<Medication>
) : RecyclerView.Adapter<MedicationPagerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvExplanation = itemView.findViewById<TextView>(R.id.tvExplanation)
        fun bind(item: Medication) {
            tvTitle.text = item.name
             }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ivRead.setOnClickListener {
            updateItem(position)
        }


        holder.bind(items[position])
    }

    private fun updateItem(position: Int) {
    }
}