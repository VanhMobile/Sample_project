package com.example.pnlib_ph35035.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pnlib_ph35035.databinding.ItemRecyclerViewPersonBinding
import com.example.pnlib_ph35035.model.Customer
import com.example.pnlib_ph35035.view.GetCustomer

class CustomerAdapter(var listCustomer: List<Customer>, val click: Click)
    : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    inner class CustomerViewHolder(val binding: ItemRecyclerViewPersonBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bin(customer: Customer){
                binding.idCustomer.text = customer.idCustomer
                binding.cardNameCus.text = customer.name
                binding.tvNumberPhone.text = customer.numberPhone
                binding.item.setOnClickListener {
                    click.clickItem(customer)
                }
                binding.item.setOnLongClickListener {
                    click.deleteCustomer(customer)
                    true
                }
            }
    }

    interface Click{
        fun clickItem(customer: Customer)
        fun deleteCustomer(customer: Customer)
    }

    fun setData(listCustomer: List<Customer>){
        this.listCustomer = listCustomer
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : CustomerViewHolder {
        val binding =  ItemRecyclerViewPersonBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent
            ,false)

        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        listCustomer?.let{
            return listCustomer.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = listCustomer[position]
        holder.bin(customer)
    }
}
