package com.example.pnlib_ph35035

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.databinding.ActivityCreatBookBinding
import com.example.pnlib_ph35035.databinding.BottomSheetCreateBookBinding
import com.example.pnlib_ph35035.databinding.BottomSheetDialogTitleBookBinding
import com.example.pnlib_ph35035.model.Book
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.lang.NullPointerException

class CreateBook : AppCompatActivity() {

    private lateinit var binding: ActivityCreatBookBinding
    private lateinit var bottomImgBookDialog: BottomSheetDialog
    private lateinit var bottomDialog: BottomSheetDialog
    private lateinit var imgUri: Uri
    private lateinit var book: Book
    private var uriString = ""
    private var category = ""
    private var quantity = 1
    private var title = ""
    private var price = 10000
    private var color = "orange"
    private var status = "save"
    val someActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                imgUri = result?.data?.data!!
                binding.addImg.setImageURI(imgUri)
                uriString = getPathFromUri(imgUri)!!
                bottomImgBookDialog.dismiss()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {


        // btn thoát
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this@CreateBook,MainActivity::class.java))
            finish()
        }

        // thêm ảnh bìa của sách
        binding.addImg.setOnClickListener {
            showDialogAddImg()
        }

        val categorys = listOf<String>("Sách Nấu Ăn","Truyện Anime","Sách Kinh Tế","Truyện Ngôn tình")
        val adapterSpinnerCate = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorys)
        adapterSpinnerCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapterSpinnerCate

        try {
            book = intent.getSerializableExtra("book") as Book

            book?.let {
                binding.addImg.setImageBitmap(BitmapFactory.decodeFile(it.imgPath))
                binding.tvTitle.text = it.nameBook
                binding.tvPrice.text = it.price.toString()
                binding.tvQuantity.text = it.quantity.toString()
                uriString = it.imgPath
                status = "updata"

                when(it.category){
                    "Sách Nấu Ăn" -> binding.spinnerCategory.setSelection(0)
                    "Truyện Anime" -> binding.spinnerCategory.setSelection(1)
                    "Sách Kinh Tế" -> binding.spinnerCategory.setSelection(2)
                    "Truyện Ngôn tình" -> binding.spinnerCategory.setSelection(3)
                }
            }
        }catch (e : NullPointerException){

        }

        binding.title.setOnClickListener {
            showBottomSheet("Tiêu đề",binding.tvTitle)
        }

        binding.price.setOnClickListener {
            showBottomSheet("Giá",binding.tvPrice)
        }

        binding.quantity.setOnClickListener {
            showBottomSheet("Số Lượng",binding.tvQuantity)
        }

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = categorys[position]
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.orange.setOnClickListener {
            color = "orange"
        }
        binding.tomato.setOnClickListener {
            color = "tomato"
        }

        binding.salmon.setOnClickListener {
            color = "salmon"
        }

        binding.magenta.setOnClickListener {
            color = "magenta"
        }

        binding.btnSaveBook.setOnClickListener {
            if (status == "save"){
                title = binding.tvTitle.text.toString()

                try {
                    quantity = binding.tvQuantity.text.toString().toInt()
                    price = binding.tvPrice.text.toString().toInt()
                    if (uriString.isNullOrEmpty()){
                        Toast.makeText(this@CreateBook,"Chưa có ảnh",Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    PNLibDataBase.getInstance(this).PNLibDao().insertBook(Book(null,uriString,title,category,quantity,price,color))
                    Toast.makeText(this@CreateBook,"Thêm Thành công",Toast.LENGTH_SHORT).show()
                    binding.tvTitle.text = "Tiêu đề"
                    binding.tvQuantity.text = "Số lượng"
                    binding.tvPrice.text = "Giá"
                    binding.addImg.setImageDrawable(resources.getDrawable(R.drawable.add_img))
                }catch (e: Exception){
                    Toast.makeText(this@CreateBook,"Tiền sô lượng phải là số",Toast.LENGTH_SHORT).show()
                }
            }else{
                title = binding.tvTitle.text.toString()
                status = "save"
                try {
                    quantity = binding.tvQuantity.text.toString().toInt()
                    price = binding.tvPrice.text.toString().toInt()
                    book.nameBook = title
                    book.price = price
                    book.quantity = quantity
                    book.category = category
                    book.imgPath = uriString
                    book.color = color
                    PNLibDataBase.getInstance(this).PNLibDao().upDataBook(book)
                    binding.tvTitle.text = "Tiêu đề"
                    binding.tvQuantity.text = "Số lượng"
                    binding.tvPrice.text = "Giá"
                    binding.addImg.setImageDrawable(resources.getDrawable(R.drawable.add_img))
                }catch (e: NumberFormatException){
                    Toast.makeText(this@CreateBook,"Tiền sô lượng phải là số",Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun showBottomSheet(text: String, tv: TextView) {
        val bottomSheetBinDing = BottomSheetDialogTitleBookBinding.inflate(layoutInflater)
        bottomDialog = BottomSheetDialog(this, R.style.BottomSheetDialogThem)
        bottomDialog.setContentView(bottomSheetBinDing.root)
        bottomSheetBinDing.resul.hint = text
        bottomSheetBinDing.imgBack.setOnClickListener {
            bottomDialog.dismiss()
        }

        bottomSheetBinDing.title.text = text
        bottomSheetBinDing.save.setOnClickListener {
            tv.text = bottomSheetBinDing.resul.text.trim()
            bottomDialog.dismiss()
        }

        bottomDialog.show()
    }

    private fun showDialogAddImg() {
        // lấy binding ánh xạ view của dialog
        val dialogBinDing = BottomSheetCreateBookBinding.inflate(layoutInflater)

        // khỏi tạo bottomsheet và ánh xạ view vào cho nó R.style là lấy style của dialog tự tạo trong theme
        bottomImgBookDialog = BottomSheetDialog(this, R.style.BottomSheetDialogThem)
        bottomImgBookDialog.setContentView(dialogBinDing.root)

        dialogBinDing.library.setOnClickListener {
            checkPermissionGallry()
        }

        dialogBinDing.cancle.setOnClickListener {
            bottomImgBookDialog.dismiss()
        }

        bottomImgBookDialog.show()

    }

    // check permission xem người dùng có cho truy cập đến thư viện ko
    private fun checkPermissionGallry() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
            .withListener(
                object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        gallery()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@CreateBook,
                            "bạn chưa cấp quyền",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        permissionRationaleShouldBeShown()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        permissionRationaleShouldBeShown()
                    }

                }
            ).onSameThread().check()
    }


    // nếu người dùng từ chối quyền mà sau đó add ảnh thì tb đến người dùng đến cài đặt
    private fun permissionRationaleShouldBeShown() {
        AlertDialog.Builder(this)
            .setMessage("Bạn chưa cấp quyền cho máy ảnh hãy đến cài đặt")
            .setPositiveButton("đến cài đặt") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            .setNegativeButton("thoát") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath:String? = null
        var cursor = this.contentResolver.query(contentUri,null,null,null,null)
        if (cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        someActivityForResult.launch(intent)
    }
}