package com.goweiwen.ivleandroid

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        webView.setWebViewClient(object : WebViewClient() {
            // shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) requires
            // minimum API 21, we're supporting API 15
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (!url.substring(0, 24).equals("http://localhost/?token=")) return false

                onLogin(url.substring(24))
                return true
            }
        })

        webView.loadUrl("https://ivle.nus.edu.sg/api/login/?apikey=$APIKEY&url=http://localhost")

    }

    fun onLogin(token: String) {
        val settings: SharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        with (settings.edit()) {
            putString("token", token)
            commit()
        }

        // TODO: send intent logged in

        this.finish()
    }
}
