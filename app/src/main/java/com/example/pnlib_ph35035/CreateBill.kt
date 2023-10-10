package com.example.pnlib_ph35035

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.location.LocationRequestCompat.Quality
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.adapter.AutoCompleteTextView
import com.example.pnlib_ph35035.adapter.BillAdapter
import com.example.pnlib_ph35035.databinding.ActivityCreateBillBinding
import com.example.pnlib_ph35035.funtions.CheckAccount
import com.example.pnlib_ph35035.model.Bill
import com.example.pnlib_ph35035.model.Book
import com.example.pnlib_ph35035.model.Customer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateBill : AppCompatActivity(){

    private lateinit var createBillBinding: ActivityCreateBillBinding
    private lateinit var booksInBill: MutableList<Book>
    private lateinit var book: Book
    private var quantity = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createBillBinding = ActivityCreateBillBinding.inflate(layoutInflater)
        setContentView(createBillBinding.root)

        initView()
    }

    private fun initView() {
        createBillBinding.imgBack.setOnClickListener {
            finish()
        }
        booksInBill = mutableListOf()
        val intent = intent
        book = intent.getSerializableExtra("book") as Book
        quantity = intent.getIntExtra("quantityBill",1)
        booksInBill.add(book)
        val adapter = BillAdapter(booksInBill,quantity)
        createBillBinding.listCarShopping.adapter = adapter
        createBillBinding.listCarShopping.layoutManager = LinearLayoutManager(this@CreateBill)
        createBillBinding.sumPrice.text = "Tổng Tiền: " + (book.price * quantity) +"Đ"
        createBillBinding.btnSaveBill.setOnClickListener {
            createBill()
        }
    }

    private fun createBill() {
        val idCustomer = createBillBinding.idCustomer.text.toString()
        val nameCustomer = createBillBinding.nameCustomer.text.toString()
        val numberPhoneCustomer = createBillBinding.edtNumberPhone.text.toString()
        val idBill = createBillBinding.edtIdBill.text.toString()
        if (idCustomer.isNullOrEmpty()
            || nameCustomer.isNullOrEmpty()
//            || !CheckAccount.isNumberPhone(numberPhoneCustomer)
            || idBill.isNullOrEmpty()){
            Toast.makeText(this,"Lỗi trường dữ liệu",Toast.LENGTH_SHORT).show()
            return
        }

        if (CheckAccount.findIdBill(idBill,this@CreateBill)){
            Toast.makeText(this,"ID của bill bị trùng",Toast.LENGTH_SHORT).show()
            return
        }

        if (CheckAccount.findIdCustomer(idCustomer,this@CreateBill)){
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val dateBill = currentDateTime.format(formatter)
            val bill = Bill(idBill,idCustomer,dateBill,book.idBook.toString(),quantity,"chưa trả")
            PNLibDataBase.getInstance(this).PNLibDao().insertBill(bill)
            Toast.makeText(this,"Tạo đơn hành thành công",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@CreateBill,MainActivity::class.java))
            finish()
        }else{
            val customer = Customer(idCustomer,nameCustomer,numberPhoneCustomer)
            PNLibDataBase.getInstance(this).PNLibDao().insertCustomer(customer)
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val dateBill = currentDateTime.format(formatter)
            val bill = Bill(idBill,idCustomer,dateBill,book.idBook.toString(),quantity,"chưa trả")
            PNLibDataBase.getInstance(this).PNLibDao().insertBill(bill)
            Toast.makeText(this,"Tạo đơn hành thành công",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@CreateBill,MainActivity::class.java))
            finish()
        }

    }



}