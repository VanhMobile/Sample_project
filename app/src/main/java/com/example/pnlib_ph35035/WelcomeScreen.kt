package com.example.pnlib_ph35035

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)
        setContentView(R.layout.activity_wellcome_screen)

        initView()
    }

    private fun initView() {
        val handler = Handler()

        val runnable = Runnable{
            val intent = Intent(this@WelcomeScreen, Login::class.java)
            startActivity(intent)
        }

        handler.postDelayed(runnable,5000)
    }
}