package hr.algebra.nasaapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.nasaapp.framework.setBooleanPreference
import hr.algebra.nasaapp.framework.startActivity

class NasaReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //FOREGROUND
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()

    }
}