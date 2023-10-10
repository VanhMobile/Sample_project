package com.example.pnlib_ph35035

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)
        setContentView(loginBinding.root)

        loginBinding.loginButton.setOnClickListener {
            val id = loginBinding.username.text.toString()
            val password = loginBinding.password.text.toString()

            val employee = PNLibDataBase.getInstance(this).PNLibDao().checkIdEmployee(id, password)
            employee?.let {
                employee.status = "Log In"
                PNLibDataBase.getInstance(this).PNLibDao().upDataPassword(employee)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}