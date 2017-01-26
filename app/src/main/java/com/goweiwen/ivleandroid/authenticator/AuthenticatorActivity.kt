package com.goweiwen.ivleandroid.authenticator

import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.kittinunf.fuel.Fuel
import com.goweiwen.ivleandroid.APIKEY
import com.goweiwen.ivleandroid.R
import kotlinx.android.synthetic.main.activity_authenticator.*


class AuthenticatorActivity : AccountAuthenticatorActivity() {
    private lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticator)

        accountManager = AccountManager.get(this)

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

        println(token)
        Fuel.get("https://ivle.nus.edu.sg/api/Lapi.svc/UserName_Get?APIKey=$APIKEY&Token=$token")
                .responseString { request, response, result ->
                    val (data, error) = result

                    if (data != null) {
                        val username = data.substring(1, data.length - 1)

                        val account = Account(username, ACCOUNT_TYPE)
                        accountManager.addAccountExplicitly(account, token, null)
                        accountManager.setAuthToken(account, AUTHTOKEN_TYPE, token)

                        val intent = Intent()
                        intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, token)
                        setAccountAuthenticatorResult(intent.extras)
                        setResult(Activity.RESULT_OK, intent)

                        finish()
                    }
                }

    }
}

