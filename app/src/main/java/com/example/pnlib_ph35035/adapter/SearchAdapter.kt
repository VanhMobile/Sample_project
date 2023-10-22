package com.example.pnlib_ph35035.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.pnlib_ph35035.R
import com.example.pnlib_ph35035.databinding.ItemSearchBinding
import com.example.pnlib_ph35035.model.Book

class SearchAdapter(
    context: Context,
    objects: List<Book>,
    val click: ClickItemSearch
) : ArrayAdapter<Book>(context, R.layout.item_search,objects){
    private val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSearchBinding.inflate(inflater,parent,false)
        val item = getItem(position)
        binding.bookName.text = item?.nameBook
        binding.itemSearch.setOnClickListener {
            item?.let {
                click.clickSearch(item)
            }
        }

        return binding.root
    }

    interface ClickItemSearch{
        fun clickSearch(book: Book)
    }
}