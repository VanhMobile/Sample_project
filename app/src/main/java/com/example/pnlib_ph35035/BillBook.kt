package com.example.pnlib_ph35035

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pnlib_ph35035.databinding.ActivityBillBookBinding
import com.example.pnlib_ph35035.view.GetBill

class BillBook : AppCompatActivity() {

    private lateinit var billBinding: ActivityBillBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        billBinding = ActivityBillBookBinding.inflate(layoutInflater)
        setContentView(billBinding.root)
        replaceFragment(GetBill(),false)
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = this@BillBook.supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container_bill,fragment).commit()
    }
}