
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

class const val MEASUREMENT_ID = "measurement_id"
class EditMeasurementActivity : AppCompatActivity() {

    private lateinit var etMeasurementName: EditText
    private lateinit var etParameterName: EditText
    private lateinit var etParameterUnit: EditText
    private var measurementId: Int = 0 // Initialize this with the actual measurement ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_measurement)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        etMeasurementName = findViewById(R.id.etMeasurementName)
        etParameterName = findViewById(R.id.etParameterName)
        etParameterUnit = findViewById(R.id.etParameterUnit)

        val saveMeasurementButton: Button = findViewById(R.id.saveMeasurementButton)
        saveMeasurementButton.setOnClickListener {
            saveMeasurement()
        }

        // Get the measurement ID from the intent
        measurementId = intent.getIntExtra(MEASUREMENT_ID, 0)
        loadMeasurementData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun loadMeasurementData() {
        val mediLogixService = MediLogixService()
        mediLogixService.getMeasurementWithParameters(measurementId) { isSuccess, measurement, parameters, error ->
            if (isSuccess && measurement != null && parameters != null) {
                // Populate EditText fields with measurement data
                etMeasurementName.setText(measurement.name)
                // Since we're editing, we assume there's only one parameter associated with the measurement
                if (parameters.isNotEmpty()) {
                    val parameter = parameters.first()
                    etParameterName.setText(parameter.name)
                    etParameterUnit.setText(parameter.unit)
                }
            } else {
                // Failed to load measurement data
                Toast.makeText(
                    this,
                    error ?: "Failed to load measurement data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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

        // Call the service method to update the measurement with parameter
        val mediLogixService = MediLogixService()
        mediLogixService.updateMeasurementWithParameter(measurementId, measurementName, parameterName, parameterUnit, "note") { success, errorMessage ->
            if (success) {
                // Measurement updated successfully
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                // Failed to update measurement
                Toast.makeText(
                    this,
                    errorMessage ?: "Failed to update measurement",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

