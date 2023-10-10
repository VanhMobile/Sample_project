package com.example.pnlib_ph35035.funtions

import android.content.Context
import android.util.Patterns
import com.example.pnlib_ph35035.DataBase.PNLibDataBase

class CheckAccount {
    companion object{
        fun isEmailFormat(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun checkPassword(password: String): Boolean {
            val pattern = Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$")
            return pattern.matches(password)
        }

        fun checkID(idEmployee: String, context: Context) : Boolean{


            return false
        }

        fun isNumberPhone(numberPhone: String): Boolean{
            val pattern = Regex("/^0\\d{9}\$/")
            return pattern.matches(numberPhone)
        }

        fun findIdCustomer(id : String,context: Context) : Boolean{
            val listIdCustomer = PNLibDataBase.getInstance(context).PNLibDao().getCustomer()
            val result = listIdCustomer.find { it.idCustomer == id.uppercase() }
            result?.let {
                return true
            }
            return false
        }

        fun findIdEmployee(id : String,context: Context) : Boolean{
            val listIdEmployee = PNLibDataBase.getInstance(context).PNLibDao().getAllEmployees()
            val result = listIdEmployee.find { it.idEmployee == id}
            result?.let {
                return true
            }
            return false
        }

        fun findIdBill(id : String,context: Context) : Boolean{
            val bill = PNLibDataBase.getInstance(context).PNLibDao().getBill(id)
            bill?.let {
                return true
            }
            return false
        }
    }
}