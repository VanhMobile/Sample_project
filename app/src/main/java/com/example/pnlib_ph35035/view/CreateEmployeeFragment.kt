package com.example.pnlib_ph35035.view

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.R
import com.example.pnlib_ph35035.databinding.BottomSheetCreateBookBinding
import com.example.pnlib_ph35035.databinding.BottomSheetDialogTitleBookBinding
import com.example.pnlib_ph35035.databinding.FragmentCreateEmployeeBinding
import com.example.pnlib_ph35035.funtions.CheckAccount
import com.example.pnlib_ph35035.model.Employee
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class CreateEmployeeFragment : BaseFragment() {

    private lateinit var createEmpBinding: FragmentCreateEmployeeBinding
    private lateinit var bottomImgBookDialog: BottomSheetDialog
    private lateinit var bottomDialog: BottomSheetDialog
    private lateinit var imgUri: Uri
    private var uriString = ""

    val someActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                imgUri = result?.data?.data!!
                createEmpBinding.employeeImg.setImageURI(imgUri)
                uriString = getPathFromUri(imgUri)!!
                bottomImgBookDialog.dismiss()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        createEmpBinding = FragmentCreateEmployeeBinding.inflate(inflater,container,false)
        initView()
        return createEmpBinding.root
    }

    private fun initView() {
        createEmpBinding.imgBack.setOnClickListener {
            replaceFragment(GetEmployeeFragment(),false)
        }

        createEmpBinding.employeeImg.setOnClickListener {
            showDialogAddImg()
        }

        createEmpBinding.id.setOnClickListener {
            showBottomSheet("Mã nhân viên", createEmpBinding.tvId)
        }

        createEmpBinding.name.setOnClickListener {
            showBottomSheet("Họ và tên",createEmpBinding.tvName)
        }

        createEmpBinding.email.setOnClickListener {
            showBottomSheet("Email",createEmpBinding.tvEmail)
        }

        createEmpBinding.pass.setOnClickListener {
            showBottomSheet("Password",createEmpBinding.tvPassword)
        }

        createEmpBinding.btnSaveEmployee.setOnClickListener {
             createEmp()
        }

    }

    private fun createEmp() {
        val id = createEmpBinding.tvId.text.toString()!!
        val name = createEmpBinding.tvName.text.toString()!!
        val officeDuty = "Thủ thư"
        val email = createEmpBinding.tvEmail.text.toString()!!
        val pass = createEmpBinding.tvPassword.text.toString()!!
        if (uriString.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Chưa có ảnh nhân viên",Toast.LENGTH_SHORT).show()
            return
        }
        val employee = Employee(id,name,officeDuty,email,pass,uriString,"Log out")
        PNLibDataBase.getInstance(requireContext()).PNLibDao().insertEmployee(employee)
        Toast.makeText(requireContext(),"Thêm nhân viên thành công",Toast.LENGTH_SHORT).show()

        createEmpBinding.employeeImg.setImageDrawable(resources.getDrawable(R.drawable.add_img))
        createEmpBinding.tvId.text = "mã"
        createEmpBinding.tvName.text = "tên"
        createEmpBinding.tvPassword.text = "mật khẩu"
        createEmpBinding.tvEmail.text = "email"
    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean){
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        if (isTransition){
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.fragment_container_create_employee,fragment).commit()
    }


    private fun checkPermissionGallry() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
            .withListener(
                object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        gallery()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(
                            context,
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
        AlertDialog.Builder(requireContext())
            .setMessage("Bạn chưa cấp quyền cho máy ảnh hãy đến cài đặt")
            .setPositiveButton("đến cài đặt") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
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
        var cursor = requireActivity().contentResolver.query(contentUri,null,null,null,null)
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

    private fun showDialogAddImg() {
        // lấy binding ánh xạ view của dialog
        val dialogBinDing = BottomSheetCreateBookBinding.inflate(layoutInflater)

        // khỏi tạo bottomsheet và ánh xạ view vào cho nó R.style là lấy style của dialog tự tạo trong theme
        bottomImgBookDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem)
        bottomImgBookDialog.setContentView(dialogBinDing.root)

        dialogBinDing.library.setOnClickListener {
            checkPermissionGallry()
        }

        dialogBinDing.cancle.setOnClickListener {
            bottomImgBookDialog.dismiss()
        }

        bottomImgBookDialog.show()

    }

    private fun showBottomSheet(text: String, tv: TextView) {
        val bottomSheetBinDing = BottomSheetDialogTitleBookBinding.inflate(layoutInflater)
        bottomDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogThem)
        bottomDialog.setContentView(bottomSheetBinDing.root)
        bottomSheetBinDing.resul.hint = text
        bottomSheetBinDing.gy.visibility = View.GONE
        bottomSheetBinDing.imgBack.setOnClickListener {
            bottomDialog.dismiss()
        }

        bottomSheetBinDing.title.text = text
        when(text){
            "Email" ->{
                bottomSheetBinDing.save.setOnClickListener {
                    if (CheckAccount.isEmailFormat(bottomSheetBinDing.resul.text.toString())){
                        tv.text = bottomSheetBinDing.resul.text.trim()
                        bottomDialog.dismiss()
                    }else{
                        bottomSheetBinDing.gy.visibility = View.VISIBLE
                        bottomSheetBinDing.gy.text = "sai định dạng email"
                        return@setOnClickListener
                    }
                }
            }
            "Mã nhân viên" ->{
                bottomSheetBinDing.save.setOnClickListener {
                    val  id = bottomSheetBinDing.resul.text.toString().trim()
                    if(!CheckAccount.findIdEmployee(id,requireContext())){
                        tv.text = bottomSheetBinDing.resul.text.trim()
                        bottomDialog.dismiss()
                    }else{
                        bottomSheetBinDing.gy.visibility = View.VISIBLE
                        bottomSheetBinDing.gy.text = "Mã đã tồn tại"
                    }
                }
            }

            "Password" ->{
                bottomSheetBinDing.save.setOnClickListener {
                    if (CheckAccount.checkPassword(bottomSheetBinDing.resul.text.toString())){
                        tv.text = bottomSheetBinDing.resul.text.trim()
                        bottomDialog.dismiss()
                    }else{
                        bottomSheetBinDing.gy.visibility = View.VISIBLE
                        bottomSheetBinDing.gy.text = "password dai 8 ký tự có 1 chữ hoa 1 số 1 ký tự đặc biệt"
                        return@setOnClickListener
                    }
                }
            }
            else ->{
                bottomSheetBinDing.save.setOnClickListener {
                    tv.text = bottomSheetBinDing.resul.text.trim()
                    bottomDialog.dismiss()
                }
                bottomSheetBinDing.gy.visibility = View.GONE
            }
        }
        bottomDialog.show()
    }

}