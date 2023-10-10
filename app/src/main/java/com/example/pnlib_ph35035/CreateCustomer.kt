package com.example.pnlib_ph35035

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pnlib_ph35035.databinding.ActivityCustomerBinding
import com.example.pnlib_ph35035.view.GetCustomer

class CreateCustomer : AppCompatActivity() {

    private lateinit var customerBinding: ActivityCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customerBinding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(customerBinding.root)
        replaceFragment(GetCustomer(),false)
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = this@CreateCustomer.supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container_create_customer,fragment).commit()
    }
}