package com.example.pnlib_ph35035.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pnlib_ph35035.databinding.ItemSearchBinding
import com.example.pnlib_ph35035.model.Employee

class HistoryAdapter(var listHistory: List<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSearchBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bin(resul : String){
            binding.bookName.text = resul
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    fun setData(listHistory: List<String>){
        this.listHistory = listHistory
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resul = listHistory[position]
        holder.bin(resul)
    }
}