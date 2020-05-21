package com.sayhitoiot.desafio_android_evandro_costa.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.sayhitoiot.desafio_android_evandro_costa.features.list.view.ActivityList

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.runOnUiThread {
            val handle = Handler()
            handle.postDelayed(Runnable {
                startActivity(Intent(this, ActivityList::class.java))
                finish()
            },
                2000
            )
        }
    }
}
