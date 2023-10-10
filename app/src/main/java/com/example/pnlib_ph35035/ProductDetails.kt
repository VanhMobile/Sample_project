package com.example.pnlib_ph35035

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.databinding.ActivityProductDetailsBinding
import com.example.pnlib_ph35035.model.Book
import java.text.NumberFormat
import java.util.Locale

class ProductDetails : AppCompatActivity() {

    private lateinit var productBinding: ActivityProductDetailsBinding
    private lateinit var book: Book
    var quantityBill = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productBinding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(productBinding.root)
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

        productBinding.tvUpdataBook.setOnClickListener {
            val intent = Intent(this@ProductDetails,CreateBook::class.java)
            intent.putExtra("book", book)
            startActivity(intent)
        }

        book = intent.getSerializableExtra("book") as Book

        book?.let {
            productBinding.imgBook.setImageBitmap(BitmapFactory.decodeFile(it.imgPath))
            productBinding.titleBook.text = it.nameBook
            productBinding.cateBook.text = "Thể loại: " + it.category
            val loansBook = PNLibDataBase.getInstance(this).PNLibDao().bookReturn("chưa trả", it.idBook!!)
            Log.e("sl", loansBook.toString())
            val quantityLeft = it.quantity - loansBook
            if (quantityLeft > 0){
                productBinding.quantityBook.text = "Số lượng còn : " + (it.quantity - loansBook)
            }else{
                productBinding.btnAddBill.visibility = View.GONE
                productBinding.viewQuantity.visibility = View.GONE
                productBinding.quantityBook.text = "hết sách"
            }
            productBinding.btnPlus.setOnClickListener {
                if (quantityBill < quantityLeft){
                    quantityBill++
                    productBinding.quantityBill.text = quantityBill.toString()
                }else{
                    quantityBill = 1
                    productBinding.quantityBill.text = quantityBill.toString()
                }
            }

            productBinding.btnDown.setOnClickListener {
                if (quantityBill > 1){
                    quantityBill--
                    productBinding.quantityBill.text = quantityBill.toString()
                }else{
                    quantityBill = 1
                    productBinding.quantityBill.text = quantityBill.toString()
                }
            }
            productBinding.priceBook.text = "Giá: " + numberFormat.format(it.price)
            productBinding.watchedBook.text= PNLibDataBase.getInstance(this).PNLibDao().loansBook(book.idBook!!).toString()
        }

        productBinding.imgBack.setOnClickListener {
            finish()
        }

        productBinding.btnAddBill.setOnClickListener {
            val intent = Intent(this@ProductDetails, CreateBill::class.java)
            intent.putExtra("book",book)
            intent.putExtra("quantityBill",quantityBill)
            startActivity(intent)
        }

    }
}