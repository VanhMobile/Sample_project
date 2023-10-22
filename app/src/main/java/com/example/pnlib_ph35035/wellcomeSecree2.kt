package com.example.pnlib_ph35035

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class wellcomeSecree2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellcome_secree2)
        initView()
    }

    private fun initView() {
        val handler = Handler()

        val runnable = Runnable{
            val intent = Intent(this@wellcomeSecree2, Login::class.java)
            startActivity(intent)
        }

        handler.postDelayed(runnable,5000)
    }
}