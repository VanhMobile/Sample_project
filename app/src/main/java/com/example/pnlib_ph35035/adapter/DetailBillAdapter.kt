package com.example.pnlib_ph35035.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pnlib_ph35035.R
import com.example.pnlib_ph35035.databinding.ItemRecyclerViewBillBookBinding
import com.example.pnlib_ph35035.model.DetailBill
import java.text.NumberFormat
import java.util.Locale

class DetailBillAdapter( var listDetailBill: List<DetailBill>, val click: Click)
    : RecyclerView.Adapter<DetailBillAdapter.DetailBillViewHolder>() {

    inner class DetailBillViewHolder(val binding: ItemRecyclerViewBillBookBinding)
        :RecyclerView.ViewHolder(binding.root) {

            fun bin(detailBill: DetailBill){
                val numberFormat = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                binding.nameCustomer.text = "Tên: " + detailBill.nameBill + "\n " + "SĐT: " +detailBill.numberPhoneBill
                binding.imgBook.setImageBitmap(BitmapFactory.decodeFile(detailBill.imgPathBill))
                binding.nameBook.text = detailBill.nameBookBill
                binding.quantityBook.text = "x" + detailBill.quantityBill.toString()
                binding.priceBook.text = numberFormat.format(detailBill.priceBill)
                binding.sumPrice.text = numberFormat.format(detailBill.quantityBill * detailBill.priceBill)
                binding.btnStatus.setOnClickListener {
                    click.clickItem(detailBill.idBill)
                }

                if (detailBill.status == "chưa trả"){
                    binding.status.setBackgroundResource(R.drawable.status_book_red_bg)
                    binding.btnStatus.visibility = View.VISIBLE
                }else{
                    binding.status.setBackgroundResource(R.drawable.status_book)
                    binding.btnStatus.visibility = View.GONE
                }
            }


    }

    fun setData(listDetailBill: List<DetailBill>){
        this.listDetailBill = listDetailBill
        notifyDataSetChanged()
    }

    interface Click{
        fun clickItem(idBill : String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : DetailBillViewHolder {
        val binding = ItemRecyclerViewBillBookBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        return DetailBillViewHolder(binding)
    }



    override fun onBindViewHolder(holder: DetailBillViewHolder, position: Int) {
        val detailBill = listDetailBill[position]
        holder.bin(detailBill)
    }



    override fun getItemCount(): Int {
        listDetailBill?.let {
            return listDetailBill.size
        }
        return 0
    }

}