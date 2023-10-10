package com.example.pnlib_ph35035

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pnlib_ph35035.databinding.ActivityMainBinding
import com.example.pnlib_ph35035.model.Employee
import com.example.pnlib_ph35035.view.AccountFragment
import com.example.pnlib_ph35035.view.ChartFragment
import com.example.pnlib_ph35035.view.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initView()
    }

    private fun initView() {

        replaceFragment(HomeFragment(),false)
        mainBinding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment(),false)
                R.id.nav_chart -> replaceFragment(ChartFragment(),false)
                R.id.nav_account -> replaceFragment(AccountFragment(),false)

                else -> {
                    replaceFragment(HomeFragment(),false)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = this@MainActivity.supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit()
    }

}