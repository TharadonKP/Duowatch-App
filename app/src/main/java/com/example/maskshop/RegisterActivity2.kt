package com.example.maskshop

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RegisterActivity2 : AppCompatActivity() {

    var editTextUsername: EditText? = null
    var editTextPassword: EditText? = null
    var editTextFirstName: EditText? = null
    var editTextLastName: EditText? = null
    var editTextMobilePhone: EditText? = null
    var editTextEmail: EditText? = null
    var btnUpdate: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        //find to widgets on a layout
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextMobilePhone = findViewById(R.id.editTextMobilePhone)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextEmail = findViewById(R.id.editTextEmail)
        btnUpdate = findViewById(R.id.btnUpdate)


        btnUpdate!!.setOnClickListener {
            register()
        }
    }

    private fun register()
    {
        var url: String = getString(R.string.root_url) + getString(R.string.user_url)
        val okHttpClient = OkHttpClient()
        val formBody: RequestBody = FormBody.Builder()
                .add("firstName", editTextFirstName?.text.toString())
                .add("lastName", editTextLastName?.text.toString())
                .add("phoneNumber", editTextMobilePhone?.text.toString())
                .add("username", editTextUsername?.text.toString())
                .add("password", editTextPassword?.text.toString())
                .add("email", editTextEmail?.text.toString())
                .add("userTypeID", "3")
                .build()

        val request: Request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                try {
                    val data = JSONObject(response.body!!.string())
                    if (data.length() > 0) {
                        Toast.makeText(this, "สมัครสมาชิกเรียบร้อยแล้ว", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
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