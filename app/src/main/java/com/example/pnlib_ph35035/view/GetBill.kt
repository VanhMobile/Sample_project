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
import com.example.pnlib_ph35035.adapter.DetailBillAdapter
import com.example.pnlib_ph35035.databinding.FragmentGetBillBinding
import com.example.pnlib_ph35035.model.Customer
import com.example.pnlib_ph35035.model.DetailBill

class GetBill : Fragment(), DetailBillAdapter.Click {

    private lateinit var getBinding: FragmentGetBillBinding
    private lateinit var adapter: DetailBillAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getBinding = FragmentGetBillBinding.inflate(inflater,container,false)
        initView()
        return getBinding.root
    }

    private fun initView() {
        val listBill = PNLibDataBase.getInstance(requireContext()).PNLibDao().getAllBill()
        adapter = DetailBillAdapter(listBill,this)
        getBinding.recyclerviewAllBill.adapter = adapter
        getBinding.recyclerviewAllBill.layoutManager = LinearLayoutManager(requireContext())

        getBinding.imgBack.setOnClickListener {
            startActivity(Intent(requireActivity(),MainActivity::class.java))
        }
    }

    override fun clickItem(idBill: String) {
       showDiaLogDelete(idBill)
    }

    private fun showDiaLogDelete(idBill: String) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Thông báo")
        dialog.setMessage("Xác nhận khách chả sách")
        dialog.setPositiveButton("oke"){ dialog,which ->
            val bill = PNLibDataBase.getInstance(requireContext()).PNLibDao().getBill(idBill)
            bill.status = "đã trả"
            PNLibDataBase.getInstance(requireContext()).PNLibDao().upDataStatusBill(bill)
            adapter.setData(PNLibDataBase.getInstance(requireContext()).PNLibDao().getAllBill())
        }
        dialog.setNegativeButton("cancel"){ dialog,which ->
            dialog.cancel()
        }
        dialog.show()
    }

}