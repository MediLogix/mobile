package hr.algebra.nasaapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hr.algebra.nasaapp.api.MediLogixService
import hr.algebra.nasaapp.model.Measurement
import hr.algebra.nasaapp.model.Parameter

class CreateMeasurementActivity : AppCompatActivity() {

    private lateinit var etMeasurementName: EditText
    private lateinit var etParameterName: EditText
    private lateinit var etParameterUnit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_measurement)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        etMeasurementName = findViewById(R.id.etMeasurementName)
        etParameterName = findViewById(R.id.etParameterName)
        etParameterUnit = findViewById(R.id.etParameterUnit)

        val saveMeasurementButton: Button = findViewById(R.id.saveMeasurementButton)

        // Save measurement when the button is clicked
        saveMeasurementButton.setOnClickListener {
            saveMeasurement()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun saveMeasurement() {
        // Get measurement name from EditText
        val measurementName = etMeasurementName.text.toString().trim()

        // Get parameter name and unit from EditText
        val parameterName = etParameterName.text.toString().trim()
        val parameterUnit = etParameterUnit.text.toString().trim()

        // Check if measurement name is empty
        if (measurementName.isEmpty()) {
            Toast.makeText(this, "Please enter measurement name", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if parameter name is empty
        if (parameterName.isEmpty()) {
            Toast.makeText(this, "Please enter parameter name", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if parameter unit is empty
        if (parameterUnit.isEmpty()) {
            Toast.makeText(this, "Please enter parameter unit", Toast.LENGTH_SHORT).show()
            return
        }

        // Call the service method to save the measurement with parameter
        val mediLogixService = MediLogixService()
        mediLogixService.createMeasurementWithParameter(measurementName, parameterName, parameterUnit, "note") { success, errorMessage ->
            if (success) {
                // Measurement saved successfully
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                // Failed to save measurement
                Toast.makeText(
                    this,
                    errorMessage ?: "Failed to save measurement",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
