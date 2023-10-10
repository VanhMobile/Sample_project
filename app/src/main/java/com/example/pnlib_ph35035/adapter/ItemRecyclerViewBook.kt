package com.example.pnlib_ph35035.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pnlib_ph35035.databinding.ItemRecyclerViewBookBinding
import com.example.pnlib_ph35035.model.Book

class ItemRecyclerViewBook(var listBooks: List<Book>, val click: Click) : RecyclerView.Adapter<ItemRecyclerViewBook.ViewHolderBook>() {

    inner class ViewHolderBook(val itemBinding: ItemRecyclerViewBookBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

            fun bin(book: Book){
                itemBinding.imgBook.setImageBitmap(BitmapFactory.decodeFile(book.imgPath))
                itemBinding.nameBook.text = book.nameBook
                itemBinding.imgBook.setOnLongClickListener {
                    click.deleteBook(book)
                    true
                }

                itemBinding.imgBook.setOnClickListener {
                    click.clickItemBook(book)
                }
            }
    }

    fun setData(list: List<Book>){
        listBooks = list
        notifyDataSetChanged()
    }

    interface Click{
        fun deleteBook(book: Book)
        fun clickItemBook(book: Book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBook {
        val itemBinding = ItemRecyclerViewBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderBook(itemBinding)
    }


    override fun getItemCount(): Int {
        listBooks?.let {
            return listBooks.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolderBook, position: Int) {
        val book = listBooks[position]
        holder.bin(book)
    }
}