package com.example.pnlib_ph35035.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pnlib_ph35035.databinding.ItemRecyclerViewEmployeeBinding
import com.example.pnlib_ph35035.model.Employee

class EmployeeAdapter(var listEmp: List<Employee>,val click: Click) : RecyclerView.Adapter<EmployeeAdapter.EmpViewHolder>() {
    inner class EmpViewHolder(val binding: ItemRecyclerViewEmployeeBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bin(employee: Employee){
            binding.idEmployee.text ="Mã: "+ employee.idEmployee
            binding.cardNameEmployee.text = employee.name
            binding.officeDuty.text = "Chức vụ: " + employee.officeDuty
            binding.email.text ="Email: " + employee.email
            binding.imgEmployee.setImageBitmap(BitmapFactory.decodeFile(employee.imgPathEmp))

            binding.item.setOnLongClickListener {
                click.delete(employee)
                true
            }
        }
    }

    fun setData(listEmp: List<Employee>){
        this.listEmp = listEmp
        notifyDataSetChanged()
    }

    interface Click{
        fun delete(employee: Employee)
        fun upData(employee: Employee)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpViewHolder {
        val binding = ItemRecyclerViewEmployeeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EmpViewHolder(binding)
    }


    override fun getItemCount(): Int {
        listEmp?.let {
            return listEmp.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {
        val employee = listEmp[position]
        holder.bin(employee)
    }
}