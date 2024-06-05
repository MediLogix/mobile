package hr.algebra.nasaapp

import android.R
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.algebra.nasaapp.databinding.ActivitySetupBinding
import hr.algebra.nasaapp.fragment.AccountFragment
import hr.algebra.nasaapp.fragment.ConfirmAccountFragment
import hr.algebra.nasaapp.fragment.NewAccountFragment
import hr.algebra.nasaapp.fragment.RecoverAccountFragment
import hr.algebra.nasaapp.fragment.TermsOfServiceFragment

class SetupActivity : FragmentActivity() {
    private lateinit var binding: ActivitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = listOf(
            TermsOfServiceFragment(),
            NewAccountFragment(),
            RecoverAccountFragment(),
        )
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        })
        val adapter = ViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter
    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val fragments: List<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}