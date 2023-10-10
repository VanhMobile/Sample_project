package com.example.pnlib_ph35035.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.MainActivity
import com.example.pnlib_ph35035.R
import com.example.pnlib_ph35035.adapter.CustomerAdapter
import com.example.pnlib_ph35035.databinding.FragmentGetCustomerBinding
import com.example.pnlib_ph35035.model.Customer
import com.example.pnlib_ph35035.model.Employee

class GetCustomer : Fragment(), CustomerAdapter.Click {

    private lateinit var getCustomerBinding: FragmentGetCustomerBinding
    private lateinit var adapter: CustomerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getCustomerBinding = FragmentGetCustomerBinding.inflate(inflater,container,false)
        initView()
        return getCustomerBinding.root
    }

    private fun initView() {
        getCustomerBinding.btnAddCustomer.setOnClickListener {
            replaceFragment(AddCustomer(),true)
        }

        getCustomerBinding.imgBack.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        val customers = PNLibDataBase.getInstance(requireContext()).PNLibDao().getCustomer()
        adapter = CustomerAdapter(customers,this)

        getCustomerBinding.recyclerviewAllCustomer.adapter = adapter
        getCustomerBinding.recyclerviewAllCustomer.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container_create_customer,fragment).commit()
    }

    override fun clickItem(customer: Customer) {

    }

    override fun deleteCustomer(customer: Customer) {
        showDiaLogDelete(customer)
    }

    private fun showDiaLogDelete(customer: Customer) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Thông báo")
        dialog.setMessage("bạn chắc chắn muốn xóa?")
        dialog.setPositiveButton("oke"){ dialog,which ->
            PNLibDataBase.getInstance(requireContext()).PNLibDao().deleteCustomer(customer)
            adapter.setData(PNLibDataBase.getInstance(requireContext()).PNLibDao().getCustomer())
            Toast.makeText(context,"Xóa thành công", Toast.LENGTH_SHORT).show()
        }
        dialog.setNegativeButton("cancel"){ dialog,which ->
            dialog.cancel()
        }
        dialog.show()
    }
}