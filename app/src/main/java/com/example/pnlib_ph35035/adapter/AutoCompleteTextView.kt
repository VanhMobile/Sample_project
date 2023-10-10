package com.example.pnlib_ph35035.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.pnlib_ph35035.R
import com.example.pnlib_ph35035.databinding.ItemAutoCompleteBinding
import com.example.pnlib_ph35035.model.Customer

class AutoCompleteTextView(
    context: Context,
    objects: List<Customer>,
    val click: ClickItemSearch
) : ArrayAdapter<Customer>(context, R.layout.item_auto_complete,objects){
    private val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemAutoCompleteBinding.inflate(inflater,parent,false)
        val item = getItem(position)
        binding.tvName.text = item?.name
        binding.tvid.text = item?.idCustomer
        binding.tvNumberPhone.text = item?.numberPhone
        binding.itemCompleteText.setOnClickListener {
            item?.let { it -> click.clickItem(it) }
        }
        return binding.root
    }

    interface ClickItemSearch{
        fun clickItem(customer: Customer)
    }
}