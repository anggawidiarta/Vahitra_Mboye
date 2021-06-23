package com.example.vahitra.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vahitra.R
import com.example.vahitra.admin.adapter.PentasAdapter
import com.example.vahitra.model.Film
import com.google.firebase.database.*

class DaftarPentas : AppCompatActivity() {

//    lateinit var btn_hapus_pentas: Button
//    lateinit var btn_edit_pentas: Button
    lateinit var pentas_list: RecyclerView
    lateinit var btnTambah : ImageView

    lateinit var database: DatabaseReference
    private var datalist = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_pentas)


        database = FirebaseDatabase.getInstance().getReference("Film")
//        btn_hapus_pentas = findViewById(R.id.btn_hapus)
//        btn_edit_pentas = findViewById(R.id.btn_edit_pentas)
        pentas_list = findViewById(R.id.pentas_list)
        btnTambah = findViewById(R.id.iv_tambah_image)

        pentas_list.layoutManager = LinearLayoutManager(this)

        getData()


        btnTambah.setOnClickListener {
            val intent = Intent(this, TambahPentas::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()
                for(getDataSnapshot in snapshot.children){
                    var film = getDataSnapshot.getValue(Film::class.java)
                    datalist.add(film!!)
                }
                pentas_list.adapter = PentasAdapter(datalist){

                }


            }

            override fun onCancelled(databaseerror: DatabaseError) {
                Toast.makeText(this@DaftarPentas, " "+databaseerror.message, Toast.LENGTH_LONG).show()
            }

        })
    }


}