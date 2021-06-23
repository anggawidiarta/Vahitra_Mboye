package com.example.vahitra.profile

import android.icu.number.Precision.currency
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.vahitra.R
import com.example.vahitra.sign.User
import com.example.vahitra.utils.Preferences
import com.google.firebase.database.*
import java.text.NumberFormat
import java.util.*

class WalletActivity : AppCompatActivity() {

    lateinit var tv_saldo_info: TextView

    lateinit var databaseUser: DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        tv_saldo_info = findViewById(R.id.tv_saldo_info)

        databaseUser = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(applicationContext)

        databaseUser.child(preferences.getValues("user").toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                currency(user?.saldo!!.toDouble(), tv_saldo_info)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
    private fun currency(harga: Double, textView: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.setText(format.format(harga))
    }
}