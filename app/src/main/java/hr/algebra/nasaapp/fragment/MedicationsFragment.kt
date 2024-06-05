package hr.algebra.nasaapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.adapter.ItemAdapter
import hr.algebra.nasaapp.adapter.MedicationsAdapter
import hr.algebra.nasaapp.databinding.FragmentItemsBinding
import hr.algebra.nasaapp.databinding.FragmentMedicationsBinding
import hr.algebra.nasaapp.framework.fetchItems
import hr.algebra.nasaapp.framework.fetchMedications
import hr.algebra.nasaapp.model.Item
import hr.algebra.nasaapp.model.Medication

class MedicationsFragment : Fragment() {

    private lateinit var medications: MutableList<Medication>
    private lateinit var binding: FragmentMedicationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        medications = requireContext().fetchMedications(1)
        binding = FragmentMedicationsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MedicationsAdapter(requireContext(), medications)
        }
    }

}