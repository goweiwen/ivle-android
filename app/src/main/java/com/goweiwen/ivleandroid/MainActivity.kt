package com.goweiwen.ivleandroid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings: SharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        val token = settings.getString("token", null)
        if (token == null) login()
        else {
            // TODO: validate token
        }
    }

    fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
