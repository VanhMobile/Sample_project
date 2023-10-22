package com.example.pnlib_ph35035

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.adapter.HistoryAdapter
import com.example.pnlib_ph35035.adapter.SearchAdapter
import com.example.pnlib_ph35035.databinding.SearchActivityBinding
import com.example.pnlib_ph35035.model.Book
import com.example.pnlib_ph35035.model.History

class SearchActivity : AppCompatActivity(), SearchAdapter.ClickItemSearch {

    private lateinit var searchBinding: SearchActivityBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)
        initView()
    }

    private fun initView() {
        searchBinding.cancelTv.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        var listBook = PNLibDataBase.getInstance(this).PNLibDao().getAllBook()
        var historys = PNLibDataBase.getInstance(this).PNLibDao().getAllHistory()
        var searchAdapter = SearchAdapter(this,listBook,this)
        historyAdapter = HistoryAdapter(historys)
        searchBinding.history.adapter = historyAdapter
        searchBinding.history.layoutManager = LinearLayoutManager(this)
        searchBinding.search.setAdapter(searchAdapter)
        searchBinding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        searchBinding.deleteIcon.setOnClickListener {
            showDiaLogDelete()
        }


    }

    override fun clickSearch(book: Book) {
        PNLibDataBase.getInstance(this).PNLibDao().insertHistory(History(null,book.nameBook))
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra("book",book)
        startActivity(intent)
    }

    private fun showDiaLogDelete() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Thông báo")
        dialog.setMessage("bạn chắc chắn muốn xóa?")
        dialog.setPositiveButton("oke"){ dialog,which ->
           PNLibDataBase.getInstance(this).PNLibDao().deleteHistory()
            historyAdapter.setData(PNLibDataBase.getInstance(this).PNLibDao().getAllHistory())
            Toast.makeText(this,"Xóa thành công", Toast.LENGTH_SHORT).show()
        }
        dialog.setNegativeButton("cancel"){ dialog,which ->
            dialog.cancel()
        }
        dialog.show()
    }


}