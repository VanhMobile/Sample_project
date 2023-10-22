package com.example.pnlib_ph35035.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.R
import com.example.pnlib_ph35035.databinding.BottomSheetCreateBookBinding
import com.example.pnlib_ph35035.databinding.FragmentCreateCustomerBinding
import com.example.pnlib_ph35035.funtions.CheckAccount
import com.example.pnlib_ph35035.model.Customer
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddCustomer : Fragment() {

    private lateinit var addCustomerBinding : FragmentCreateCustomerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCustomerBinding = FragmentCreateCustomerBinding.inflate(layoutInflater)
        initView()
        return addCustomerBinding.root
    }

    private fun initView() {
        addCustomerBinding.errorMessId.visibility = View.GONE
        addCustomerBinding.errorMessPhone.visibility = View.GONE
        addCustomerBinding.errorMessName.visibility = View.GONE

        addCustomerBinding.btnSaveCustomer.setOnClickListener {
            val name = addCustomerBinding.edtName.text.toString()
            val id = addCustomerBinding.edtIdCustomer.text.toString()
            val phone = addCustomerBinding.edtNumberPhone.text.toString()
            if (id.isNullOrEmpty()){
                addCustomerBinding.errorMessId.visibility = View.VISIBLE
                addCustomerBinding.errorMessId.text = "id không được trống"
                return@setOnClickListener
            }else{
                addCustomerBinding.errorMessId.visibility = View.GONE
                if (CheckAccount.findIdCustomer(id,requireContext())){
                    addCustomerBinding.errorMessId.visibility = View.VISIBLE
                    addCustomerBinding.errorMessId.text = "id bị trùng"
                    return@setOnClickListener
                }
            }

            if (name.isNullOrEmpty()){
                addCustomerBinding.errorMessName.visibility = View.VISIBLE
                addCustomerBinding.errorMessName.text = "tên không được trống"
                return@setOnClickListener
            }else{
                addCustomerBinding.errorMessId.visibility = View.GONE
            }

            if (!CheckAccount.isNumberPhone(phone)){
                addCustomerBinding.errorMessPhone.visibility = View.VISIBLE
                addCustomerBinding.errorMessPhone.text = "sai định dạng số"
                return@setOnClickListener
            }else{
                addCustomerBinding.errorMessPhone.visibility = View.GONE
            }

            val customer = Customer(id.uppercase(),name,phone)
            PNLibDataBase.getInstance(requireActivity()).PNLibDao().insertCustomer(customer)
            Toast.makeText(requireActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show()

            addCustomerBinding.edtName.setText("")
            addCustomerBinding.edtIdCustomer.setText("")
            addCustomerBinding.edtNumberPhone.setText("")
        }

        addCustomerBinding.imgBack.setOnClickListener {
            replaceFragment(GetCustomer(),false)
        }

        val listGender = listOf<String>("Nam","Nữ","Giới tính khác")
        val adapterSpinnerGen = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listGender)
        adapterSpinnerGen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        addCustomerBinding.gender.adapter = adapterSpinnerGen
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container_create_customer,fragment).commit()
    }
}