package com.example.pnlib_ph35035.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.databinding.ItemRecyclerViewCategoryBinding
import com.example.pnlib_ph35035.model.Book
import java.text.NumberFormat
import java.util.Locale

class AllBookAdapter(val listBooks: List<Book>,val context: Context,val click: Click) : RecyclerView.Adapter<AllBookAdapter.AllBookViewHolder>() {
    inner class AllBookViewHolder(val binding: ItemRecyclerViewCategoryBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bin(book: Book){
                val numberFormat = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
                binding.imgBook.setImageBitmap(BitmapFactory.decodeFile(book.imgPath))
                binding.nameBook.text = book.nameBook
                binding.priceBook.text = "Giá: "+ numberFormat.format(book.price)
                val loansBook = PNLibDataBase.getInstance(context).PNLibDao().bookReturn("chưa trả", book.idBook!!)
                binding.quantityBook.text = "Số lượng còn: " + (book.quantity - loansBook)
                binding.watchedBook.text = PNLibDataBase.getInstance(context).PNLibDao().loansBook(book.idBook!!).toString()

                binding.item.setOnClickListener {
                    click.clickItem(book)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBookViewHolder {
        val binding = ItemRecyclerViewCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllBookViewHolder(binding)
    }

    interface Click{
        fun clickItem(book: Book)
    }
    override fun getItemCount(): Int {
        listBooks?.let {
            return listBooks.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: AllBookViewHolder, position: Int) {
        val book = listBooks[position]
        holder.bin(book)
    }
}