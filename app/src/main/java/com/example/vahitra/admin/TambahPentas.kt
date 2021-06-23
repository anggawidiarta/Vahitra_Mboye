package com.example.vahitra.admin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vahitra.databinding.ActivityTambahPentasBinding
import com.example.vahitra.model.Film
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class TambahPentas : AppCompatActivity() {

    private lateinit var binding : ActivityTambahPentasBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference

    //atribut
    private lateinit var fileUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahPentasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Film")
        storageReference = FirebaseStorage.getInstance().reference

        binding.btnEditPentas.setOnClickListener {
            addData()
            val intent = Intent(this, AddPosterPentas::class.java)
            intent.putExtra(AddPosterPentas.EXTRA_DATA, binding.namaPentas.text.toString())
            startActivity(intent)
        }

    }

    private fun saveToFirebase(value : String) {
        databaseReference.child("film")
    }

    private fun addData() {
        val title = binding.namaPentas.text.toString()
        val genre = binding.tanggalPentas.text.toString()
        val director = binding.namaProduksi.text.toString()
        val description = binding.sinopsisPentas.text.toString()
        val rating = binding.pemainPentas.text.toString()

        if (title.isEmpty()){
            binding.namaPentas.error = "Judul anda kosong"
            binding.namaPentas.requestFocus()
        } else if (genre.isEmpty()){
            binding.tanggalPentas.error = "Genre anda kosong"
            binding.tanggalPentas.requestFocus()
        } else if(rating.isEmpty()){
            binding.pemainPentas.error = "Rating anda kosong"
            binding.pemainPentas.requestFocus()
        } else if (director.isEmpty()){
            binding.namaProduksi.error = "Director anda kosong"
            binding.namaProduksi.requestFocus()
        } else if (description.isEmpty()){
            binding.sinopsisPentas.error = "Description anda kosong"
            binding.sinopsisPentas.requestFocus()
        } else {
            submitData(Film(
                description,
                director,
                genre,
                title,
                rating
            ))
        }
    }

    private fun submitData(film: Film) {
        databaseReference.child("Film").push()
            .setValue(film).addOnSuccessListener {
                Toast.makeText(this, "Film baru berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
    }
}