package com.application.News.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment

import com.application.News.R
import com.application.News.util.FileUtils
import com.application.News.util.UtilInterface
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_imageselect.view.*
import java.io.File
import java.io.IOException


class ImageSelectorDialog {

    constructor(activity: Activity, action: Action){
        this.activity = activity
        this.action = action
        create()
    }

    constructor(fragment: Fragment, action: Action){
        this.fragment = fragment
        this.action = action
        create()
    }

    var activity: Activity? = null
    var fragment: Fragment? = null
    var action: Action
    var dialog: BottomSheetDialog? = null
    var currentPhotoPath :String? = null


    private fun create(){
        dialog = BottomSheetDialog(getContext())
        currentPhotoPath = String()

        val view = ViewHelper.createView(getContext(), R.layout.dialog_imageselect)

        val cameraClick = View.OnClickListener {
            dialog?.dismiss()
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    getContext(),
                    "com.application.News.provider",
                    it
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
               // activity?.startActivityForResult(cameraIntent, CAMERA)
            }

            activity?.let { it.startActivityForResult(cameraIntent, CAMERA) }
            fragment?.let { it.startActivityForResult(cameraIntent, CAMERA) }
        }

        val galleryClick = View.OnClickListener {
            dialog?.dismiss()
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            val chooser = Intent.createChooser(intent, "Choose a Picture")
            activity?.let { it.startActivityForResult(chooser, GALLERY) }
            fragment?.let { it.startActivityForResult(chooser, GALLERY) }
        }

        val cancelClick = View.OnClickListener {
            dialog?.dismiss()
        }

        view.rlCamera.setOnClickListener(cameraClick)
        view.rlGallery.setOnClickListener(galleryClick)
        view.textCancel.setOnClickListener(cancelClick)

        dialog?.setContentView(view)
        dialog?.show()
    }

    private fun createImageFile(): File? {
        val current = System.currentTimeMillis()
        val timeStamp = (current / 1000).toString()
        val storageDir: File = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            timeStamp, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.d("TAG", "createImageFile: "+currentPhotoPath)
        }

    }


    fun processActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        when(resultCode == Activity.RESULT_OK && data != null){
            true -> {
                when (requestCode) {
                    CAMERA -> {
                        try {

                            val path = currentPhotoPath
                            val file = File(path)
                            path.let {
                                action.onImageSelected(path!!,file.name)
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                    GALLERY -> {
                        val uri = data.data
                        val imagePath = FileUtils.getPath(getContext(),uri!!)
                        if (!TextUtils.isEmpty(imagePath)){
                            val file = File(imagePath!!)
                            imagePath.let {
                                action.onImageSelected(imagePath,file.name)
                            }
                        }

                    }
                }
            }
            else -> {
                ((activity ?: fragment) as UtilInterface).toast("Image not Selected!")
            }
        }
    }



    private fun getContext(): Context = (activity ?: fragment!!.context) as Context


    companion object{

        val CAMERA = 1
        val GALLERY = 2

    }

    interface Action{
        fun onImageSelected(imagePath: String,filename:String)
    }
}