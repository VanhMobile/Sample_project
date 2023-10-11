package com.example.pnlib_ph35035.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pnlib_ph35035.databinding.ItemRecyclerViewBillBinding
import com.example.pnlib_ph35035.model.Book
import java.text.NumberFormat
import java.util.Locale

class BillAdapter(var books: List<Book>, val quantityBill: Int) : RecyclerView.Adapter<BillAdapter.ViewHolderBill>() {

    inner class ViewHolderBill(val binding: ItemRecyclerViewBillBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bin(book: Book){
                val numberFormat = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                binding.imgBook.setImageBitmap(BitmapFactory.decodeFile(book.imgPath))
                binding.nameBook.text = book.nameBook
                binding.priceBook.text = numberFormat.format(book.price)
                binding.quantityBook.text = "x"+ quantityBill.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ViewHolderBill {
        val binding = ItemRecyclerViewBillBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent
            ,false)

        return ViewHolderBill(binding)
    }

    override fun getItemCount(): Int {
        books?.let {
            return books.size
        }

        return 0
    }

    override fun onBindViewHolder(holder: ViewHolderBill, position: Int) {
        val book = books[position]
        holder.bin(book)
    }
}