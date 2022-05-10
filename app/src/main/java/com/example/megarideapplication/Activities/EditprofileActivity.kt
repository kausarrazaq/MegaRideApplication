package com.example.megarideapplication.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.megarideapplication.Models.UpdateUserImageModel
import com.example.megarideapplication.R
import com.example.megarideapplication.WebServices.POSTService
import com.example.megarideapplication.utilis.AppURL
import com.example.megarideapplication.utilis.ShareMemory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File

class EditprofileActivity : AppCompatActivity(), POSTService.ResponseInterface{
    private lateinit var saveBtn: Button
    private lateinit var backPressedIcon: ImageView
    private lateinit var editName: View
    private lateinit var profileImage: ImageView
    private lateinit var editImage: ImageView
    private lateinit var shareMemory: ShareMemory
    private var isCamera: Boolean = true
    private var imageUrl = ""
    private lateinit var userNameTv: TextView
    private lateinit var emailTv: TextView
    private lateinit var passwordTv: TextView

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val CAMERA_REQUEST = 1888

    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        saveBtn = findViewById(R.id.savebtn)
        backPressedIcon = findViewById(R.id.backpreessed)
        editName = findViewById(R.id.editname)
        profileImage = findViewById(R.id.profileimage)
        editImage = findViewById(R.id.changeimage)
        userNameTv = findViewById(R.id.usernameTv)
        passwordTv = findViewById(R.id.passwordTv)
        emailTv= findViewById(R.id.emailtv)
        shareMemory = ShareMemory.mInstence
        shareMemory = ShareMemory.getmInstence()


        if (shareMemory.profileImageUrl.isNotEmpty()){
            Glide.with(this)
                .load(shareMemory.profileImageUrl)
                .placeholder(R.drawable.profileicon)
                .into(profileImage)
        }



        editName.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = this.layoutInflater.inflate(R.layout.bottomsheetforeditimage, null)
        with(bottomSheetDialog) {
            setContentView(bottomSheetView)
        }
        editImage.setOnClickListener {
            showDialogNotificationAction(bottomSheetDialog)
        }
        bottomSheetView.findViewById<LinearLayout>(R.id.camBtn).setOnClickListener {
            askCameraPermission()
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<LinearLayout>(R.id.galBtn).setOnClickListener {
            askGalleryPermission()
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<LinearLayout>(R.id.cancelBtn).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        backPressedIcon.setOnClickListener {
            finish()
        }
        saveBtn.setOnClickListener {
            finish()
        }
    }

    //foropencamera
    private fun openCamera() {
        isCamera = true
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)

        } else {
            openCamera()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) ==
            PackageManager.PERMISSION_DENIED
        ) {
            //permission denied
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            //show popup to request runtime permission
            requestPermissions(permissions, IMAGE_PICK_CODE)
        } else {
            openGalleryForImage()
        }
    }


   //foropengallery
    private fun openGalleryForImage() {
        isCamera = false
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun bitmapToByte(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray,
    ) {   /*grantResults.isNotEmpty()*/
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            IMAGE_PICK_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    openGalleryForImage()
                } else {
                    //permission from popup denied
                    Toast.makeText(this,"Gallery Permission denied", Toast.LENGTH_SHORT).show()
                    // ShareResource().showAlertMessage(this, "Gallery Permission denied", false)
                }
            }
            CAMERA_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this,"Camera Permission denied", Toast.LENGTH_SHORT).show()
                    // ShareResource().showAlertMessage(this, "Camera Permission denied", false)
                }
            }
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                if (isCamera) {
                    val data = result.data
                    val bitmap: Bitmap = data?.extras?.get("data") as Bitmap
                    profileImage.setImageBitmap(bitmap)
                    imageUrl = bitmapToByte(bitmap)
                    updateimagefun()
                } else {
                    val data: Intent? = result.data
                    val uri: Uri? = data?.data
                    val inputStream =this.contentResolver.openInputStream(uri!!)
                    val cursor = this.contentResolver.query(uri, null, null, null, null)
                    cursor?.use { c ->
                        val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (c.moveToFirst()) {
                            val name = c.getString(nameIndex)
                            inputStream?.let { inputStream ->
                                // create same file with same name
                                val file = File(this.cacheDir, name)
                                val os = file.outputStream()
                                os.use {
                                    inputStream.copyTo(it)
                                }
                                val options: BitmapFactory.Options = BitmapFactory.Options()
                                options.inSampleSize = 3
                                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                                profileImage.setImageBitmap(bitmap)
                                imageUrl = bitmapToByte(bitmap)
                                updateimagefun()

                            }
                        }
                    }
                }
            }
        }
    override fun onResume() {
        super.onResume()
        userNameTv.text=shareMemory.userName
        emailTv.text= shareMemory.userEmail
    }


    private fun showDialogNotificationAction(bottomSheetDialog: BottomSheetDialog) {
        bottomSheetDialog.show()
        val bottomSheetDialogFrameLayout =
            bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetDialogFrameLayout?.background = null
    }

    private fun updateimagefun() {
        val jsonObject = JSONObject()


        jsonObject.put("user_id", shareMemory.userId)
        jsonObject.put("image", imageUrl)


        val postService = POSTService(this, this)
        postService.putData(jsonObject, AppURL.UPDATEUSERIMAGE_URL)
    }


    override fun getResponse(o: Any?) {
        val `object` = o as JSONObject
        try {
            val status = `object`.getBoolean("status")
            if (status) {
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()
                val userData = gson.fromJson(`object`.toString(), UpdateUserImageModel::class.java)
                shareMemory.profileImageUrl = userData.data.image
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}