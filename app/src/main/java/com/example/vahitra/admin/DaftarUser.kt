package com.example.vahitra.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vahitra.R
import com.example.vahitra.admin.adapter.PentasAdapter
import com.example.vahitra.admin.adapter.UserAdapter
import com.example.vahitra.model.Film
import com.example.vahitra.sign.User
import com.google.firebase.database.*

class DaftarUser : AppCompatActivity() {

    lateinit var user_list: RecyclerView

    lateinit var database: DatabaseReference
    private var datalist = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_user)

        database = FirebaseDatabase.getInstance().getReference("User")

        user_list = findViewById(R.id.user_list)

        user_list.layoutManager = LinearLayoutManager(this)

        getData()
    }

    private fun getData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()
                for (getDataSnapshot in snapshot.children) {
                    var user = getDataSnapshot.getValue(User::class.java)
                    datalist.add(user!!)
                }

                user_list.adapter = UserAdapter(datalist){
                    
                }
            }


            override fun onCancelled(databaseerror: DatabaseError) {
                Toast.makeText(this@DaftarUser, " "+databaseerror.message, Toast.LENGTH_LONG).show()
            }

        })

    }
}