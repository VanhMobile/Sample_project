package com.example.pnlib_ph35035.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pnlib_ph35035.AllBook
import com.example.pnlib_ph35035.BillBook
import com.example.pnlib_ph35035.CreateBook
import com.example.pnlib_ph35035.CreateCustomer
import com.example.pnlib_ph35035.CreateEmployee
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.ProductDetails
import com.example.pnlib_ph35035.adapter.ItemRecyclerViewBook
import com.example.pnlib_ph35035.databinding.FragmentHomeBinding
import com.example.pnlib_ph35035.model.Book
import com.example.pnlib_ph35035.model.Customer
import com.example.pnlib_ph35035.model.Employee


class HomeFragment() : Fragment(), ItemRecyclerViewBook.Click {

    private lateinit var homeBinding: FragmentHomeBinding

    private lateinit var adapterBook : ItemRecyclerViewBook

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        initView()
        PNLibDataBase.getInstance(requireContext())
        return homeBinding.root
    }

    private fun initView() {
        val employee = PNLibDataBase.getInstance(requireContext()).PNLibDao().getEmployees("Log In")

        if (employee.officeDuty.uppercase() == "THỦ THƯ"){
            homeBinding.group.visibility = View.GONE
        }
        // thêm slider
        addSlider()

        // thêm sách
        homeBinding.book.setOnClickListener {
            startActivity(Intent(requireContext(),CreateBook::class.java))
        }
        // adapter all book
        val listBooks = PNLibDataBase.getInstance(requireContext()).PNLibDao().getAllBook()
        adapterBook = ItemRecyclerViewBook(listBooks,this)

        homeBinding.recyclerviewAll.adapter = adapterBook

        homeBinding.recyclerviewAll.layoutManager = GridLayoutManager(requireContext(),3)
        homeBinding.recyclerviewAll.isNestedScrollingEnabled = false

        val adapterTop = ItemRecyclerViewBook(PNLibDataBase.getInstance(requireContext()).PNLibDao().topTen(),this)
        homeBinding.recyclerviewBxh.adapter = adapterTop
        adapterTop.notifyDataSetChanged()

        homeBinding.recyclerviewBxh.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        homeBinding.employee.setOnClickListener {
            startActivity(Intent(requireContext(),CreateEmployee::class.java))
        }

        homeBinding.bill.setOnClickListener {
            startActivity(Intent(requireContext(),BillBook::class.java))
        }

        homeBinding.group.setOnClickListener {
            startActivity(Intent(requireContext(),CreateCustomer::class.java))
        }


        val adapterBookKt = ItemRecyclerViewBook(PNLibDataBase.getInstance(requireContext()).PNLibDao().getCateBook("Sách Kinh Tế"),this)
        homeBinding.recyclerviewKt.adapter = adapterBookKt
        homeBinding.recyclerviewKt.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        val adapterBookCook = ItemRecyclerViewBook(PNLibDataBase.getInstance(requireContext()).PNLibDao().getCateBook("Sách Nấu Ăn"),this)
        homeBinding.recyclerviewCook.adapter = adapterBookCook
        homeBinding.recyclerviewCook.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        val adapterBookAnime = ItemRecyclerViewBook(PNLibDataBase.getInstance(requireContext()).PNLibDao().getCateBook("Truyện Anime"),this)
        homeBinding.recyclerviewAnime.adapter = adapterBookAnime
        homeBinding.recyclerviewAnime.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        val adapterBookNt = ItemRecyclerViewBook(PNLibDataBase.getInstance(requireContext()).PNLibDao().getCateBook("Truyện Ngôn tình"),this)
        homeBinding.recyclerviewNt.adapter = adapterBookNt
        homeBinding.recyclerviewNt.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        homeBinding.nextAll.setOnClickListener {
            val intent = Intent(requireContext(),AllBook::class.java)
            intent.putExtra("category","Tất cả")
            startActivity(intent)
        }

        homeBinding.nextNa.setOnClickListener {
            val intent = Intent(requireContext(),AllBook::class.java)
            intent.putExtra("category","Sách Nấu Ăn")
            startActivity(intent)
        }

        homeBinding.nextNt.setOnClickListener {
            val intent = Intent(requireContext(),AllBook::class.java)
            intent.putExtra("category","Truyện Ngôn tình")
            startActivity(intent)
        }

        homeBinding.nextAnime.setOnClickListener {
            val intent = Intent(requireContext(),AllBook::class.java)
            intent.putExtra("category","Truyện Anime")
            startActivity(intent)
        }

        homeBinding.nextBxh.setOnClickListener {
            val intent = Intent(requireContext(),AllBook::class.java)
            intent.putExtra("category","top 10")
            startActivity(intent)
        }

        homeBinding.nextKt.setOnClickListener {
            val intent = Intent(requireContext(),AllBook::class.java)
            intent.putExtra("category","Sách Kinh Tế")
            startActivity(intent)
        }



    }

    private fun addSlider() {
        val listImgSlider = ArrayList<SlideModel>()
        listImgSlider.add(SlideModel("https://cungdocsach.vn/wp-content/uploads/2020/06/V%E1%BB%81-nh%C3%A0-%C4%83n-c%C6%A1m.jpg","Sách Nấu ăn"))
        listImgSlider.add(SlideModel("https://cdn.popsww.com/blog/sites/2/2022/12/top-anime-nam-dep.jpg","Anime"))
        listImgSlider.add(SlideModel("https://listsach.com/wp-content/uploads/2022/07/Top-nhung-quyen-sach-kinh-te-hoc-hay-nen-doc.jpg","Sách Kinh tế"))
        listImgSlider.add(SlideModel("https://img.websosanh.vn/v10/users/review/images/vgojzq85ea4xg/truyen-tranh-ngon-tinh-1.jpg?compress=85","Kiếm hiệp"))
        listImgSlider.add(SlideModel("https://img.meta.com.vn/Data/image/2021/10/11/truyen-tranh-ngon-tinh-tong-tai-1.jpg","Tình yêu"))
        listImgSlider.add(SlideModel("https://img.meta.com.vn/Data/image/2021/10/11/truyen-tranh-ngon-tinh-tong-tai-al.jpg","Ngôn tình"))
        listImgSlider.add(SlideModel("https://bigone.vn/uploads/images/truyen-tranh-dam-my-omega.jpg","Đam mỹ"))
        homeBinding.imageSlider.setImageList(listImgSlider,ScaleTypes.CENTER_CROP)
    }

    override fun deleteBook(book: Book) {
        showDiaLogDelete(book)
    }

    override fun clickItemBook(book: Book) {
        val intent = Intent(requireContext(), ProductDetails::class.java)
        intent.putExtra("book",book)
        startActivity(intent)
    }

    private fun showDiaLogDelete(book: Book) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Thông báo")
        dialog.setMessage("bạn chắc chắn muốn xóa?")
        dialog.setPositiveButton("oke"){ dialog,which ->
            PNLibDataBase.getInstance(requireContext()).PNLibDao().deleteBook(book)
            adapterBook.setData(PNLibDataBase.getInstance(requireContext()).PNLibDao().getAllBook())
            Toast.makeText(context,"Xóa thành công", Toast.LENGTH_SHORT).show()
        }
        dialog.setNegativeButton("cancel"){ dialog,which ->
            dialog.cancel()
        }
        dialog.show()
    }
}