package hr.algebra.nasaapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hr.algebra.nasaapp.databinding.FragmentRemindersBinding
import hr.algebra.nasaapp.model.Reminder

class RemindersFragment : Fragment() {

    private lateinit var reminders: MutableList<Reminder>
    private lateinit var binding: FragmentRemindersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //reminders = requireContext().fetchItems()
        binding = FragmentRemindersBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ReminderAdapter(requireContext(), reminders)
        }*/
    }

}