package com.example.vahitra.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.vahitra.R

class DashboardAdmin : AppCompatActivity() {

    lateinit var btn_pentas: ImageView
    lateinit var btn_faq: ImageView
    lateinit var btn_user: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)

        btn_pentas = findViewById(R.id.btn_pentas)
        btn_faq = findViewById(R.id.btn_faq)
        btn_user = findViewById(R.id.btn_user)

        btn_pentas.setOnClickListener {
            val pentas = Intent(this@DashboardAdmin, DaftarPentas::class.java)
            startActivity(pentas)
        }

        btn_faq.setOnClickListener {
            val faq = Intent(this@DashboardAdmin, DaftarFaq::class.java)
            startActivity(faq)
        }

        btn_user.setOnClickListener {
            val user = Intent(this@DashboardAdmin, DaftarUser::class.java)
            startActivity(user)
        }
    }
}