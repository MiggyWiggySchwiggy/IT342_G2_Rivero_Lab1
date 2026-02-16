package com.rivero.userauth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rivero.userauth.api.RetrofitClient
import com.rivero.userauth.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvDetails = findViewById<TextView>(R.id.tvDetails)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Fetch Protected Data
        RetrofitClient.instance.getProfile().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    tvWelcome.text = "Welcome, ${user?.firstName}!"
                    tvDetails.text = "Username: ${user?.username}\nEmail: ${user?.email}"
                } else {
                    Toast.makeText(this@DashboardActivity, "Session Expired", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                tvWelcome.text = "Error loading profile"
            }
        })

        // Logout
        btnLogout.setOnClickListener {
            RetrofitClient.instance.logout().enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                    finish()
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DashboardActivity, "Logout failed", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}