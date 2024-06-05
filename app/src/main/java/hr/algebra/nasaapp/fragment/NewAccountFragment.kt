package hr.algebra.nasaapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import hr.algebra.nasaapp.MainActivity
import hr.algebra.nasaapp.R
import hr.algebra.nasaapp.api.MediLogixService

class NewAccountFragment : Fragment() {

    private lateinit var displayNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var createAccountButton: MaterialButton
    private lateinit var loginButton: MaterialButton
    private lateinit var returnButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_account, container, false)

        // Find views by ID
        displayNameEditText = view.findViewById(R.id.displaynameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        createAccountButton = view.findViewById(R.id.createAccountButton)
        loginButton = view.findViewById(R.id.loginButton)
        returnButton = view.findViewById(R.id.returnButton)
        // Set onClick listeners
        createAccountButton.setOnClickListener {
            createAccount()
        }

        loginButton.setOnClickListener {
            navigateToNextFragment()
        }
        returnButton.setOnClickListener {
            back()
        }
        return view
    }
    private fun back() {
        requireActivity().supportFragmentManager.popBackStack()
    }
    private fun createAccount() {
        val mediLogixService = MediLogixService()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val displayName = displayNameEditText.text.toString().trim()
        if (email.isEmpty() || displayName.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please enter all data", Toast.LENGTH_SHORT).show()
            return
        }
        mediLogixService.register(requireActivity(), email, displayName, password) { success, errorMessage ->
            if (success) {
                // Start the new activity
                startNewActivity()
                // Finish the current activity to prevent going back
                finishCurrentActivity()
            } else {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun startNewActivity() {
        // Start your new activity here using an Intent
        // Replace YourNewActivity::class.java with the appropriate activity class
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    private fun finishCurrentActivity() {
        // Finish the current activity
        requireActivity().finish()
    }

    private fun navigateToNextFragment() {
        val nextFragment = RecoverAccountFragment() // Instantiate the next fragment
        requireActivity().supportFragmentManager.beginTransaction().apply {
            // Replace the current fragment with the next fragment
            replace(android.R.id.content, nextFragment)
            // Add this transaction to the back stack
            addToBackStack(null)
            // Commit the transaction
            commit()
        }
    }
}
