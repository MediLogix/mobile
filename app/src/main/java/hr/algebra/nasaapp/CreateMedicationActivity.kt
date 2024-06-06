package hr.algebra.nasaapp

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.nasaapp.api.MediLogixService
import hr.algebra.nasaapp.databinding.ActivityEditMedicationBinding

class CreateMedicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditMedicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up UI and load medication details
        setupUI()
    }

    private fun setupUI() {
        // Set up any UI components and listeners
        binding.saveButton.setOnClickListener {
            saveMedication()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun saveMedication() {
        val mediLogixService = MediLogixService()
        mediLogixService.create_medication(
            1,
            binding.etTitle.text.toString(),
            binding.etDosage.text.toString().toDoubleOrNull() ?: 0.0,
            binding.etUnit.text.toString(),
            binding.etStash.text.toString(),
            binding.etNote.text.toString(),
        ) { success, errorMessage ->
            if (success) {
                // Medication details created successfully
                Toast.makeText(this, "Medication created", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK) // Indicate that the creation was successful
                finish() // Close activity after saving
            } else {
                // Handle error
                Toast.makeText(this, errorMessage ?: "Failed to create medication", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
