package hr.algebra.nasaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.nasaapp.databinding.ActivitySplashScreenBinding
import hr.algebra.nasaapp.framework.applyAnimation
import hr.algebra.nasaapp.framework.callDelayed
import hr.algebra.nasaapp.framework.getBooleanPreference
import hr.algebra.nasaapp.framework.isOnline
import hr.algebra.nasaapp.framework.startActivity

private const val DELAY = 3000L

const val DATA_IMPORTED = "hr.algebra.nasaapp.data_imported"
const val USER_SETUP_COMPLETED = "hr.algebra.nasaapp.user_setup_completed"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        //binding.ivSplash.applyAnimation(R.anim.rotate)
        binding.tvSplash.applyAnimation(R.anim.blink)
    }

    private fun redirect() {
        if (getBooleanPreference(USER_SETUP_COMPLETED)) {
            startActivity<MainActivity>()
            finish()
        } else {
            callDelayed(DELAY){
                startActivity<SetupActivity>()
                finish()
            }
        }
    }
}