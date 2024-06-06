package hr.algebra.nasaapp

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.nasaapp.api.MediLogixService
import hr.algebra.nasaapp.databinding.ActivityEditMedicationBinding
import hr.algebra.nasaapp.model.Medication
const val MEDICATION_ID = "medication_id"

class EditMedicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMedicationBinding
    private var medicationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Extract medication ID from intent
        medicationId = intent.getStringExtra(MEDICATION_ID)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up UI and load medication details
        setupUI()
    }

    private fun setupUI() {
        // Set up any UI components and listeners
        // For example, you can set up click listener for the save button
        binding.saveButton.setOnClickListener {
            saveMedication()
        }

        // Load existing medication details
        loadMedicationDetails()
    }

    private fun loadMedicationDetails() {
        // Fetch the medication details and populate the UI
        // You need to replace this with the actual logic to fetch medication details
        val mediLogixService = MediLogixService()
        mediLogixService.get_medication(medicationId!!.toInt()) { success, medication, errorMessage ->
            if (success) {
                medication?.let {
                    binding.etTitle.setText(it.name)
                    binding.etDosage.setText(it.dosage.toString())
                    binding.etUnit.setText(it.unit)
                    binding.etStash.setText(it.stash.toString())
                    binding.etNote.setText(it.note)
                }
            } else {
                Toast.makeText(this, errorMessage ?: "Failed to load medication details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun saveMedication() {
        val mediLogixService = MediLogixService()
        mediLogixService.update_medication(
            medicationId!!.toInt(),
            binding.etTitle.text.toString(),
            binding.etDosage.text.toString().toDoubleOrNull() ?: 0.0,
            binding.etUnit.text.toString(),
            binding.etStash.text.toString(),
            binding.etNote.text.toString()
        ) { success, errorMessage ->
            if (success) {
                // Medication details updated successfully
                Toast.makeText(this, "Medication updated", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK) // Indicate that the update was successful
                finish() // Close activity after saving
            } else {
                // Handle error
                Toast.makeText(this, errorMessage ?: "Failed to update medication details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
