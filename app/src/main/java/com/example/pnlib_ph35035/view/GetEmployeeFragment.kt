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
import com.example.pnlib_ph35035.adapter.EmployeeAdapter
import com.example.pnlib_ph35035.databinding.FragmentGetEmployeeBinding
import com.example.pnlib_ph35035.model.Book
import com.example.pnlib_ph35035.model.Employee

class GetEmployeeFragment : BaseFragment(), EmployeeAdapter.Click {

    private lateinit var getEmpBinding: FragmentGetEmployeeBinding
    private lateinit var employeeAdapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getEmpBinding = FragmentGetEmployeeBinding.inflate(inflater,container,false)
        initView()
        return getEmpBinding.root


    }

    private fun initView() {
        getEmpBinding.imgBack.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        getEmpBinding.btnAddEmp.setOnClickListener {
            replaceFragment(CreateEmployeeFragment(),true)
        }

        val listEmp = PNLibDataBase.getInstance(requireContext()).PNLibDao().getAllEmployees()

        employeeAdapter = EmployeeAdapter(listEmp,this)

        getEmpBinding.recyclerviewEmployee.adapter = employeeAdapter

        getEmpBinding.recyclerviewEmployee.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container_create_employee,fragment).commit()
    }

    override fun delete(employee: Employee) {
        showDiaLogDelete(employee)
    }

    private fun showDiaLogDelete(employee: Employee) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Thông báo")
        dialog.setMessage("bạn chắc chắn muốn xóa?")
        dialog.setPositiveButton("oke"){ dialog,which ->
            PNLibDataBase.getInstance(requireContext()).PNLibDao().deleteEmployee(employee)
            employeeAdapter.setData( PNLibDataBase.getInstance(requireContext()).PNLibDao().getAllEmployees())
            Toast.makeText(context,"Xóa thành công", Toast.LENGTH_SHORT).show()
        }
        dialog.setNegativeButton("cancel"){ dialog,which ->
            dialog.cancel()
        }
        dialog.show()
    }

    override fun upData(employee: Employee) {
        TODO("Not yet implemented")
    }
}