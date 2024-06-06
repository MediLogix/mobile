package hr.algebra.nasaapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.adapter.MeasurementsAdapter
import hr.algebra.nasaapp.databinding.FragmentMeasurementsBinding
import hr.algebra.nasaapp.model.Measurement
import hr.algebra.nasaapp.model.Parameter

class MeasurementsFragment : Fragment() {

    private var _binding: FragmentMeasurementsBinding? = null
    private val binding get() = _binding!!

    private lateinit var measurementsAdapter: MeasurementsAdapter
    private val measurements: MutableList<Measurement> = mutableListOf()

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
            onEditClickListener = { /* Handle edit measurement */ },
            onDeleteClickListener = { /* Handle delete measurement */ }
        )

        // Set up RecyclerView
        binding.rvMeasurements.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = measurementsAdapter
        }

        // Set up FloatingActionButton
        binding.fab.setOnClickListener {
            // Handle adding new measurement
        }

        // Load initial data
        loadMeasurements()
    }

    private fun loadMeasurements() {
        // Simulate loading data from a data source
        val exampleMeasurements = listOf(
            Measurement(1, 1, "Measurement 1", "Note 1", listOf(
                Parameter(1, 1, "Parameter 1", "Unit 1"),
                Parameter(2, 1, "Parameter 2", "Unit 2")
            )),
            Measurement(2, 1, "Measurement 2", "Note 2", listOf(
                Parameter(3, 2, "Parameter 3", "Unit 3")
            ))
        )

        // Update dataset and notify adapter
        measurements.clear()
        measurements.addAll(exampleMeasurements)
        measurementsAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
