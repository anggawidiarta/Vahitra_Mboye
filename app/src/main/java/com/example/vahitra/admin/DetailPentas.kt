package com.example.vahitra.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.vahitra.databinding.ActivityDetailPentasBinding
import com.example.vahitra.model.Film
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailPentas : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "data"
        const val EXTRA_IMAGE = "image"
    }

    private lateinit var binding : ActivityDetailPentasBinding
    private lateinit var firebase : FirebaseDatabase
    private lateinit var database : DatabaseReference
    private lateinit var storage : StorageReference
    private lateinit var fireStorage : FirebaseStorage

    //atribut
    private lateinit var film : String
    private lateinit var poster : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPentasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebase = FirebaseDatabase.getInstance()
        database = firebase.getReference("Film")

        fireStorage = FirebaseStorage.getInstance()
        storage = fireStorage.reference

        film = intent.getStringExtra(EXTRA_DATA) as String
        poster = intent.getStringExtra(EXTRA_IMAGE) as String
        Log.d("DetailPentas", film)


        //getData
        database.child(film).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val detailFilm = snapshot.getValue(Film::class.java)
                with(binding){
                    namaPentas.setText(detailFilm?.judul.toString())
                    sinopsisPentas.setText(detailFilm?.desc)
                    pemainPentas.setText(detailFilm?.rating)
                    namaProduksi.setText(detailFilm?.director)
                    tanggalPentas.setText(detailFilm?.genre)

                    Glide.with(this@DetailPentas)
                        .load(detailFilm?.poster)
                        .circleCrop()
                        .into(this.pentasIcon)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DetailPentas", error.message.toString())
            }

        })

        binding.btnEditPentas.setOnClickListener {
            inputData()
        }

        binding.btnBack.setOnClickListener { onBackPressed() }

        binding.ivTambahImage.setOnClickListener {
            addImage()
        }
    }

    private fun addImage() {

    }

    private fun inputData(){
            val title = binding.namaPentas.text.toString()
            val director = binding.namaProduksi.text.toString()
            val description = binding.sinopsisPentas.text.toString()
            val genres = binding.tanggalPentas.text.toString()
            val rating = binding.pemainPentas.text.toString()

            val dataFilm = Film(
                desc = description,
                director = director,
                genre = genres,
                judul = title,
                poster = poster,
                rating = rating
            )
            editData(dataFilm)

    }

    private fun editData(film : Film){
        database.child(film.judul!!).setValue(film)
            .addOnSuccessListener {
               Log.d("DetailPentas", "onSuccess : Success update")
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}