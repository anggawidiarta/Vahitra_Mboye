package com.example.vahitra.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vahitra.R
import com.example.vahitra.model.Checkout
import com.example.vahitra.model.Film
import com.example.vahitra.sign.User
import com.example.vahitra.utils.Preferences
import com.google.firebase.database.*

class CheckoutActivity : AppCompatActivity() {

    private var datalist =  ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences: Preferences

    private lateinit var rv_pesanan: RecyclerView
    private lateinit var tv_total: TextView
    lateinit var btn_back: ImageView
    lateinit var saldo: TextView
    lateinit var btn_bayar: Button
    lateinit var btn_cancel: Button
    lateinit var dataFilm : Film
    lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        database = FirebaseDatabase.getInstance().getReference("User")

        rv_pesanan = findViewById(R.id.rv_pesanan)
        tv_total = findViewById(R.id.tv_total)
        btn_back = findViewById(R.id.btn_back)
        btn_bayar = findViewById(R.id.btn_tiket)
        btn_cancel = findViewById(R.id.btn_home)


        preferences = Preferences(this)
        datalist = intent.getSerializableExtra("kursi") as ArrayList<Checkout>
        dataFilm = intent.getParcelableExtra("data")!!

        for(a in datalist.indices){
            total += datalist[a].harga!!.toInt()
        }

        datalist.add(Checkout("Total Harus Dibayar", total.toString()))

        tv_total.text = total.toString()
        rv_pesanan.layoutManager = LinearLayoutManager(this)
        rv_pesanan.adapter = CheckoutAdapter(datalist){

        }

        val username = preferences.getValues("user")
        Log.d("CheckoutActivity", username.toString())

        btn_bayar.setOnClickListener{
            database.child(username.toString()).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                val count = user?.saldo?.toInt()?.minus(total).toString()
                Log.d("CheckoutActivity", count.toString())

                val userUpdate = User()
                userUpdate.email = user?.email
                userUpdate.nama = user?.nama
                userUpdate.password = user?.password
                userUpdate.url = user?.url
                userUpdate.username = user?.username
                userUpdate.saldo = count

                database.child(username.toString()).setValue(userUpdate)
            }
//            database.child(username.toString()).addValueEventListener( object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    for (data in snapshot.children){
//                        val user = data.child(username.toString()).getValue(User::class.java)
//                        user?.saldo = user?.saldo?.toInt()?.minus(total).toString()
//                        database.child(username.toString())
//                    }
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.d("CheckoutActivity", error.message)
//                }
//
//            })
            finishAffinity()
            var bayar = Intent(this@CheckoutActivity, ChekoutSuccessActivity::class.java)
            bayar.putExtra("data", dataFilm)
            startActivity(bayar)
        }

        btn_back.setOnClickListener{
            var bayar = Intent(this@CheckoutActivity, PilihBangkuActivity::class.java)
            startActivity(bayar)
        }

        btn_cancel.setOnClickListener{
            var bayar = Intent(this@CheckoutActivity, PilihBangkuActivity::class.java)
            startActivity(bayar)
        }
    }
}