package com.example.pnlib_ph35035

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pnlib_ph35035.databinding.ActivityCreatEmployeeBinding
import com.example.pnlib_ph35035.view.CreateEmployeeFragment
import com.example.pnlib_ph35035.view.GetEmployeeFragment

class CreateEmployee : AppCompatActivity() {
    private lateinit var createEmpBinding: ActivityCreatEmployeeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createEmpBinding = ActivityCreatEmployeeBinding.inflate(layoutInflater)
        setContentView(createEmpBinding.root)
        replaceFragment(GetEmployeeFragment(),false)
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container_create_employee,fragment).commit()
    }
}