package com.example.maskshop

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class UserFragment : Fragment() {
    var btnUpdate: Button? = null
    var userID: String? = null
    var txtFirstName: TextView? = null
    var txtLastName: TextView? = null
    var txtUsername: TextView? = null
    var txtPassword: TextView? = null
    var txtEmail: TextView? = null
    var txtMobilePhone: TextView? = null
    var btnDelete: Button? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_user, container, false)

        //get shared preference
        val sharedPrefer = requireContext().getSharedPreferences(
                LoginActivity().appPreference, Context.MODE_PRIVATE)
        userID = sharedPrefer?.getString(LoginActivity().userIdPreference, null)

        txtFirstName = root.findViewById(R.id.txtFirstName)
        txtLastName = root.findViewById(R.id.txtLastName)
        txtUsername = root.findViewById(R.id.txtUsername)
        txtPassword = root.findViewById(R.id.txtPassword)
        txtMobilePhone = root.findViewById(R.id.txtMobilePhone)
        txtEmail = root.findViewById(R.id.txtEmail)
        btnUpdate = root.findViewById(R.id.btnUpdate)
        btnDelete = root.findViewById(R.id.btnDelete)
        viewUser(userID)

        return root
    }

    private fun viewUser(userID: String?) {
        var url: String = getString(R.string.root_url) + getString(R.string.user_url) + userID
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
                .url(url)
                .get()
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val data = JSONObject(response.body!!.string())
                    if (data.length() > 0) {

                        txtFirstName?.text = data.getString("firstName")
                        txtLastName?.text = data.getString("lastName")
                        txtUsername?.text = data.getString("username")
                        txtPassword?.text = data.getString("password")
                        txtMobilePhone?.text = data.getString("phoneNumber")
                        txtEmail?.text = data.getString("email")


                        btnUpdate!!.setOnClickListener {
                            val bundle = Bundle()
                            bundle.putString("userID", userID)

                            val fm = UserUpdateFragment()
                            fm.arguments = bundle;

                            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.replace(R.id.nav_host_fragment, fm)
                            fragmentTransaction.commit()
                        }
                        // val fragmentTransaction = fragmentManager!!.beginTransaction()
                        btnDelete!!.setOnClickListener {
                            deleteUser(userID)
                            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.nav_host_fragment, LogoutFragment())
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                response.code
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun deleteUser(userID: String?) {
        var url: String = getString(R.string.root_url) + getString(R.string.user_url) + userID

        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
                .url(url)
                .delete()
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val data = JSONObject(response.body!!.string())
                    if (data.length() > 0) {
                        Toast.makeText(context, "ยกเลิกการสมัครสมาชิกเรียบร้อยแล้ว", Toast.LENGTH_LONG).show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            } else {
                response.code
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
