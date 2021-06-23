package com.example.vahitra.admin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.vahitra.databinding.ActivityAddPosterPentasBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class AddPosterPentas : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "data"
    }

    private lateinit var binding : ActivityAddPosterPentasBinding
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    //atribut
    private lateinit var judul : String
    private lateinit var fileUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPosterPentasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Film")
        storageReference = FirebaseStorage.getInstance().reference

        judul = intent.getStringExtra(EXTRA_DATA) as String

        binding.ivAddPoster.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .start()
        }

        binding.ivPoster.setOnClickListener {
            if (fileUri != null){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Upload Image")
                progressDialog.show()

                val ref = storageReference.child("poster/"+ UUID.randomUUID().toString())
                ref.putFile(fileUri)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Upload Image Success", Toast.LENGTH_SHORT).show()

                        ref.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())
                        }
                    }
                    .addOnFailureListener{
                        progressDialog.dismiss()
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener {
                        val progres = 100.0 * it.bytesTransferred / it.totalByteCount
                        progressDialog.setMessage("Upload ${progres.toInt()}")
                    }
            }
        }
    }

    private fun saveToFirebase(value : String) {
        databaseReference.child(judul).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val poster = value
                databaseReference.child(judul).setValue(poster)
                finishAffinity()
                val intent = Intent(this@AddPosterPentas, DashboardAdmin::class.java)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            fileUri = data?.data!!

            Glide.with(this)
                .load(fileUri)
                .circleCrop()
                .into(binding.ivPoster)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}