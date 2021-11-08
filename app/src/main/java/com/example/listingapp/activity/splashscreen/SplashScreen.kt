package com.example.listingapp.activity.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.listingapp.R
import com.example.listingapp.activity.MainActivity
import com.example.listingapp.appconfig.AppConstant

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({ gotohome() }, AppConstant.splash_timer.toLong())
    }

    private fun gotohome() {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}