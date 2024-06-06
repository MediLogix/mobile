package hr.algebra.nasaapp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.nasaapp.CreateMeasurementActivity
import hr.algebra.nasaapp.EditMeasurementActivity
import hr.algebra.nasaapp.MEASUREMENT_ID
import hr.algebra.nasaapp.adapter.MeasurementsAdapter
import hr.algebra.nasaapp.api.MediLogixService
import hr.algebra.nasaapp.databinding.FragmentMeasurementsBinding
import hr.algebra.nasaapp.model.Measurement
import hr.algebra.nasaapp.model.Parameter

class MeasurementsFragment : Fragment() {

    private var _binding: FragmentMeasurementsBinding? = null
    private val binding get() = _binding!!

    private lateinit var measurementsAdapter: MeasurementsAdapter
    private val measurements: MutableList<Measurement> = mutableListOf()
    companion object {
        private const val REQUEST_CODE_CREATE_MEASUREMENT = 1
        private const val REQUEST_CODE_EDIT_MEASUREMENT = 2
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeasurementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize adapter
        measurementsAdapter = MeasurementsAdapter(
            requireContext(),
            measurements,
            onEditClickListener = { measurement -> openEditMeasurementActivity(measurement.id) },
            onDeleteClickListener = { measurement -> deleteMeasurement(measurement) }
        )

        // Set up RecyclerView
        binding.rvMeasurements.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = measurementsAdapter
        }

        // Set up FloatingActionButton
        binding.fab.setOnClickListener {
            // Handle opening the create measurement activity
            openCreateMeasurementActivity()
        }

        // Load initial data
        loadMeasurements()
    }

    private fun deleteMeasurement(measurement: Measurement) {
        val mediLogixService = MediLogixService()
        mediLogixService.deleteMeasurement(measurement.id!!) { success, errorMessage ->
            if (success) {
                measurements.remove(measurement)
                measurementsAdapter.notifyDataSetChanged()
                Toast.makeText(context, "Measurement deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, errorMessage ?: "Failed to delete measurement", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCreateMeasurementActivity() {
        // Launch CreateMeasurementActivity
        val intent = Intent(requireContext(), CreateMeasurementActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CREATE_MEASUREMENT)
    }

    private fun openEditMeasurementActivity(measurementId: Int) {
        // Launch EditMeasurementActivity and pass the measurement ID
        val intent = Intent(requireContext(), EditMeasurementActivity::class.java)
        intent.putExtra(MEASUREMENT_ID, measurementId) // Use the parameter instead of just measurementId
        startActivityForResult(intent, REQUEST_CODE_EDIT_MEASUREMENT)
    }





    private fun loadMeasurements() {
        val mediLogixService = MediLogixService()

        mediLogixService.getMeasurementsWithParameters { isSuccess, measurementParameterPairs, error ->
            if (isSuccess && measurementParameterPairs != null) {
                // Clear existing measurements
                measurements.clear()

                // Iterate over measurement-parameter pairs
                for ((measurement, parameters) in measurementParameterPairs) {
                    // Convert parameters to your model's Parameter objects
                    val parameterObjects = parameters.map { parameter ->
                        Parameter(parameter._id!!, measurement._id, parameter.name, parameter.unit)
                    }
                    // Create a Measurement object with the fetched data
                    val measurementObject = Measurement(measurement._id!!, measurement.user, measurement.name, measurement.note, parameterObjects)
                    // Add the measurement to the list
                    measurements.add(measurementObject)
                }

                // Notify adapter about the data change
                measurementsAdapter.notifyDataSetChanged()
            } else {
                // Handle the error scenario
                Log.e("MediLogixService", "Error loading measurements with parameters: $error")
                // Optionally, show an error message to the user
                // Or retry loading measurements
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            // Check the request code to determine which activity was completed
            when (requestCode) {
                REQUEST_CODE_CREATE_MEASUREMENT, REQUEST_CODE_EDIT_MEASUREMENT -> {
                    // Reload data when returning from CreateMeasurementActivity or EditMeasurementActivity
                    loadMeasurements()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
