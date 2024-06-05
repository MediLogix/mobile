package hr.algebra.nasaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.nasaapp.adapter.ItemPagerAdapter
import hr.algebra.nasaapp.adapter.MedicationPagerAdapter
import hr.algebra.nasaapp.databinding.ActivityItemPagerBinding
import hr.algebra.nasaapp.databinding.ActivityMedicationPagerBinding
import hr.algebra.nasaapp.framework.fetchItems
import hr.algebra.nasaapp.framework.fetchMedications
import hr.algebra.nasaapp.model.Medication

const val MEDICATION_POSITION = "hr.algebra.nasaapp.medication_pos"

class MedicationPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicationPagerBinding

    private lateinit var items: MutableList<Medication>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicationPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        items = fetchMedications(1)
        position = intent.getIntExtra(POSITION, position)
        binding.viewPager.adapter = MedicationPagerAdapter(this, items)
        binding.viewPager.currentItem = position
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}