package com.example.vahitra.admin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vahitra.R
import com.example.vahitra.admin.DetailPentas
import com.example.vahitra.model.Film
import com.google.firebase.database.FirebaseDatabase


class PentasAdapter(private var data: List<Film>, private val listener:(Film) -> Unit) : RecyclerView.Adapter<PentasAdapter.ViewHolder>(){
    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PentasAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.item_pentas, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: PentasAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tv_nama_gambar: TextView = view.findViewById(R.id.tv_nama_pentas)
        private val tvImage: ImageView = view.findViewById(R.id.iv_gambar_pentas)
        private  val btn_hapus_pentas: Button =  view.findViewById(R.id.btn_hapus)
        private  val btn_edit_pentas: Button =  view.findViewById(R.id.btn_edit_pentas)


        fun bindItem(data: Film, listener: (Film) -> Unit, context: Context){
            tv_nama_gambar.setText(data.judul)

            Glide.with(context)
                    .load(data.poster)
                    .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }

            btn_hapus_pentas.setOnClickListener {
            val db = FirebaseDatabase.getInstance().getReference("Film").child(data.judul!!)

            db.removeValue().addOnSuccessListener {
                Toast.makeText( itemView.context, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show()
             }
            }

            btn_edit_pentas.setOnClickListener {
                val editpentas = Intent(itemView.context, DetailPentas::class.java)
                editpentas.putExtra(DetailPentas.EXTRA_DATA, data.judul)
                editpentas.putExtra(DetailPentas.EXTRA_IMAGE, data.poster)
                itemView.context.startActivity(editpentas)
            }

        }



    }
}