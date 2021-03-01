package com.example.maskshop

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

class LoginActivity : AppCompatActivity() {

    val appPreference: String = "appPrefer"
    val userIdPreference: String = "userIdPref"
    val usernamePreference: String = "usernamePref"
    val userTypePreference: String = "userTypePref"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //btnregis = findViewById(R.id.btnreg)
        //btnlogin = findViewById(R.id.btnLogin)

        //To run network operations on a main thread or as an synchronous task.
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val btnregis = findViewById<Button>(R.id.btnreg)
        btnregis.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity2::class.java)
            startActivity(intent)
            finish()
        }
        //Find to components on a layout
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.btnlogin)


        btnLogin.setOnClickListener {

            val url = getString(R.string.root_url) + getString(R.string.login_url)
            val okHttpClient = OkHttpClient()
            val formBody: RequestBody = FormBody.Builder()
                    .add("username", editTextUsername.text.toString())
                    .add("password", editTextPassword.text.toString())
                    .build()
            val request: Request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build()
            try {
                val response = okHttpClient.newCall(request).execute()
                if (response.isSuccessful) {
                    try {
                        val obj = JSONObject(response.body!!.string())
                        val userID = obj["userID"].toString()
                        val username = obj["username"].toString()
                        val userTypeID = obj["userTypeID"].toString()


                        //Create shared preference to store user data
                        val sharedPrefer: SharedPreferences =
                                getSharedPreferences(appPreference, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPrefer.edit()

                        editor.putString(userIdPreference, userID)
                        editor.putString(usernamePreference, username)
                        editor.putString(userTypePreference, userTypeID)
                        editor.commit()

                        //return to login page
                        if (userTypeID == "3") //0 = general users
                        {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else if (userTypeID == "2")//1 = admin
                        {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    response.code
                    Toast.makeText(applicationContext, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onResume() {
        val sharedPrefer: SharedPreferences =
                getSharedPreferences(appPreference, Context.MODE_PRIVATE)
        val usertype = sharedPrefer?.getString(userTypePreference, null)

        //if (sharedPrefer.contains(usernamePreference))
        if (usertype == "3") {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        } else if (usertype == "2") {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        super.onResume()
    }
}


