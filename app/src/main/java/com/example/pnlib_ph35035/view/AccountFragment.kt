package com.example.pnlib_ph35035.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.Login
import com.example.pnlib_ph35035.R
import com.example.pnlib_ph35035.databinding.DialogUpdataPasswordBinding
import com.example.pnlib_ph35035.databinding.FragmentAccountBinding
import com.example.pnlib_ph35035.funtions.CheckAccount
import com.example.pnlib_ph35035.model.Book
import com.example.pnlib_ph35035.model.Employee

class AccountFragment() : Fragment() {

    private lateinit var accountBinding: FragmentAccountBinding
    private lateinit var employee: Employee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountBinding = FragmentAccountBinding.inflate(inflater,container,false)
        initView()
        return accountBinding.root
    }

    private fun initView() {
        employee = PNLibDataBase.getInstance(requireContext()).PNLibDao().getEmployees("Log In")
        accountBinding.btnLogout.setOnClickListener {
            showDiaLogLogOut()
        }
        accountBinding.tvName.text = "Tên: " + employee.name
        accountBinding.tvDuty.text = "Chức vụ: " + employee.officeDuty
        accountBinding.imgAvt.setImageBitmap(BitmapFactory.decodeFile(employee.imgPathEmp))

        accountBinding.upDataPassword.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialogBinding = DialogUpdataPasswordBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        dialogBinding.errorMass.visibility = View.GONE
        dialogBinding.errorMass2.visibility = View.GONE
        dialogBinding.btnUpDataPass.setOnClickListener {
            val oldPass = dialogBinding.passwordOld.text.toString().trim()
            val newPass = dialogBinding.passwordNew.text.toString()
            if (oldPass != employee.pass){
                dialogBinding.errorMass.visibility = View.VISIBLE
                return@setOnClickListener
            }else{
                dialogBinding.errorMass.visibility = View.GONE
            }
            if (!CheckAccount.checkPassword(newPass)){
                dialogBinding.errorMass2.visibility = View.VISIBLE
                return@setOnClickListener
            }else{
                dialogBinding.errorMass2.visibility = View.GONE
            }
            if(oldPass == newPass){
                Toast.makeText(context,"trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            employee.pass = newPass
            PNLibDataBase.getInstance(requireContext()).PNLibDao().upDataPassword(employee)
            Toast.makeText(context,"Cập nhập thành công", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDiaLogLogOut() {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Thông báo")
        dialog.setMessage("Bạn muốn đăng xuất?")
        dialog.setPositiveButton("oke"){ dialog,which ->
            employee.status = "Log out"
            PNLibDataBase.getInstance(requireContext()).PNLibDao().upDataPassword(employee)
            startActivity(Intent(requireContext(),Login::class.java))
            requireActivity().finish()
            Toast.makeText(context,"Đăng xuất thành công", Toast.LENGTH_SHORT).show()
        }
        dialog.setNegativeButton("cancel"){ dialog,which ->
            dialog.cancel()
        }
        dialog.show()
    }


}