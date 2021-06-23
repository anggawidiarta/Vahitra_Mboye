package com.example.vahitra.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vahitra.R
import com.example.vahitra.sign.User

class UserAdapter(private var data: List<User>, private val listener:(User) -> Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tv_nama_user: TextView = view.findViewById(R.id.tv_nama_pentas)
        private val tv_email_user: TextView = view.findViewById(R.id.tv_email_user)
        private val tvImage: ImageView = view.findViewById(R.id.iv_gambar_pentas)

        fun bindItem(data: User, listener: (User) -> Unit, context: Context){
            tv_nama_user.setText(data.nama)
            tv_email_user.setText(data.email
            )

            Glide.with(context)
                    .load(data.url)
                    .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }

        }



    }
}