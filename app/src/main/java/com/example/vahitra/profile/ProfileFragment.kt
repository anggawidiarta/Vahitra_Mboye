package com.example.vahitra.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vahitra.R
import com.example.vahitra.sign.SignInActivity
import com.example.vahitra.utils.Preferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ProfileFragment : Fragment() {

    lateinit var tv_emails: TextView
    lateinit var tv_nama_lengkap: TextView
    lateinit var tv_wallet: TextView
    lateinit var tv_logout: TextView
    lateinit var tv_edit_profile: TextView
    lateinit var iv_users: ImageView

    lateinit var databaseUser: DatabaseReference
    lateinit var preferences: Preferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_emails = view.findViewById(R.id.tv_emails)
        tv_nama_lengkap = view.findViewById(R.id.tv_nama_lengkap)
        tv_wallet = view.findViewById(R.id.tv_wallet)
        tv_logout = view.findViewById(R.id.tv_logout)
        tv_edit_profile = view.findViewById(R.id.tv_edit_profil)
        iv_users =  view.findViewById(R.id.iv_users)

        databaseUser = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(activity!!.applicationContext)

        tv_nama_lengkap.setText(preferences.getValues("nama"))
        tv_emails.setText(preferences.getValues("email"))

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_users)


        tv_wallet.setOnClickListener {
            var intent = Intent(context, WalletActivity::class.java)
            startActivity(intent)
        }

        tv_edit_profile.setOnClickListener {
            var intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        tv_logout.setOnClickListener {
            val editor = preferences.sharedPreferences
            editor.edit().clear().commit()
            var intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}