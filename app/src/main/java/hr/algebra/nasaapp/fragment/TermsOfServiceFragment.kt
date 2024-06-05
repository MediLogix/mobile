package hr.algebra.nasaapp.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.algebra.nasaapp.databinding.FragmentTermsOfServiceBinding
import android.content.Intent
import android.net.Uri
import com.google.android.material.button.MaterialButton
import hr.algebra.nasaapp.R

class TermsOfServiceFragment : Fragment() {
    private var _binding: FragmentTermsOfServiceBinding? = null
    private lateinit var nextButton: MaterialButton
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsOfServiceBinding.inflate(inflater, container, false)
        val view = binding.root

        val fullText = "Nastavkom korištenja ove aplikacije prihvaćate naše uvjete korištenja i potvrđujete da ste upoznati s našom politikom privatnosti."
        val spannableString = SpannableString(fullText)

        val termsClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrlInBrowser("http://google.com") // Open terms of service link
            }
        }

        val privacyClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrlInBrowser("http://google.com") // Open privacy policy link
            }
        }



        // Set clickable spans for specific portions of the text
        spannableString.setSpan(termsClickableSpan, fullText.indexOf("uvjete korištenja"), fullText.indexOf("uvjete korištenja") + "uvjete korištenja".length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(privacyClickableSpan, fullText.indexOf("politikom privatnosti"), fullText.indexOf("politikom privatnosti") + "politikom privatnosti".length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.termsAndPrivacyTextView.text = spannableString
        binding.termsAndPrivacyTextView.movementMethod = LinkMovementMethod.getInstance()
        nextButton = view.findViewById(R.id.acceptButton)

        nextButton.setOnClickListener {
            navigateToNextFragment()
        }

        return view
    }
    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    private fun navigateToNextFragment() {
        val nextFragment = NewAccountFragment() // Instantiate the next fragment
        requireActivity().supportFragmentManager.beginTransaction().apply {
            // Replace the current fragment with the next fragment
            replace(android.R.id.content, nextFragment)
            // Add this transaction to the back stack
            addToBackStack(null)
            // Commit the transaction
            commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
