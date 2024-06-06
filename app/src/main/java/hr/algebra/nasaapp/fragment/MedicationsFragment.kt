package hr.algebra.nasaapp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.algebra.nasaapp.CreateMedicationActivity
import hr.algebra.nasaapp.EditMedicationActivity
import hr.algebra.nasaapp.MEDICATION_ID
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.adapter.MedicationsAdapter
import hr.algebra.nasaapp.api.MediLogixService
import hr.algebra.nasaapp.databinding.FragmentMedicationsBinding
import hr.algebra.nasaapp.model.Medication

class MedicationsFragment : Fragment() {
    private lateinit var binding: FragmentMedicationsBinding
    private var medications: MutableList<Medication> = mutableListOf()
    private lateinit var medicationsAdapter: MedicationsAdapter

    companion object {
        private const val REQUEST_CODE_CREATE_MEDICATION = 1
        private const val REQUEST_CODE_EDIT_MEDICATION = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicationsAdapter = MedicationsAdapter(requireContext(), medications,
            { medication -> openEditMedicationActivity(medication) },
            { medication -> deleteMedication(medication) }
        )

        binding.rvMedications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = medicationsAdapter
        }

        // Set up FAB click listener
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            openCreateMedicationActivity()
        }

        fetchMedications()
    }

    private fun fetchMedications() {
        val mediLogixService = MediLogixService()
        mediLogixService.get_medications(1) { success, body, errorMessage ->
            if (success) {
                medications.clear()
                body?.let {
                    medications.addAll(it)
                    medicationsAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteMedication(medication: Medication) {
        val mediLogixService = MediLogixService()
        mediLogixService.delete_medication(medication._id!!.toInt()) { success, errorMessage ->
            if (success) {
                medications.remove(medication)
                medicationsAdapter.notifyDataSetChanged()
                Toast.makeText(context, "Medication deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, errorMessage ?: "Failed to delete medication", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCreateMedicationActivity() {
        // Launch CreateMedicationActivity
        val intent = Intent(requireContext(), CreateMedicationActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CREATE_MEDICATION)
    }

    private fun openEditMedicationActivity(medication: Medication) {
        // Launch EditMedicationActivity and pass the medication ID
        val intent = Intent(requireContext(), EditMedicationActivity::class.java)
        intent.putExtra(MEDICATION_ID, medication._id.toString()) // Assuming 'id' is the medication ID
        startActivityForResult(intent, REQUEST_CODE_EDIT_MEDICATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REQUEST_CODE_CREATE_MEDICATION || requestCode == REQUEST_CODE_EDIT_MEDICATION)
            && resultCode == Activity.RESULT_OK) {
            // Refresh the medications list
            fetchMedications()
        }
    }
}
