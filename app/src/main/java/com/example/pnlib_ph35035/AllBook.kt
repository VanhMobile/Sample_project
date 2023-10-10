package com.example.pnlib_ph35035

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.adapter.AllBookAdapter
import com.example.pnlib_ph35035.databinding.ActivityAllBookBinding
import com.example.pnlib_ph35035.model.Book

class AllBook : AppCompatActivity(), AllBookAdapter.Click {

    private lateinit var allBookBinding: ActivityAllBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allBookBinding = ActivityAllBookBinding.inflate(layoutInflater)
        setContentView(allBookBinding.root)

        initView()
    }

    private fun initView() {

        val intent = intent
        val category = intent.getStringExtra("category")!!
        var list = listOf<Book>()
        when(category){
            "Tất cả" -> list = PNLibDataBase.getInstance(this).PNLibDao().getAllBook()
            "top 10" -> list = PNLibDataBase.getInstance(this).PNLibDao().topTen()
            else ->{
                list = PNLibDataBase.getInstance(this).PNLibDao().getCateBook(category)
            }
        }
        val adapter = AllBookAdapter(list,this@AllBook,this)
        allBookBinding.imgBack.setOnClickListener {
            startActivity(Intent(this@AllBook,MainActivity::class.java))
            finish()
        }
        allBookBinding.bookCategory.text = category

        allBookBinding.recyclerviewCategory.adapter = adapter
        allBookBinding.recyclerviewCategory.layoutManager = LinearLayoutManager(this)
    }

    override fun clickItem(book: Book) {
        val intent = Intent(this@AllBook, ProductDetails::class.java)
        intent.putExtra("book",book)
        startActivity(intent)
    }
}