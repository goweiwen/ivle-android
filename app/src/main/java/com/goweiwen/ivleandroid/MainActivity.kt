package com.goweiwen.ivleandroid

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.goweiwen.ivleandroid.authenticator.ACCOUNT_TYPE
import com.goweiwen.ivleandroid.authenticator.AUTHTOKEN_TYPE
import com.goweiwen.ivleandroid.authenticator.AuthenticatorActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountManager = AccountManager.get(this)
        val accounts = accountManager.accounts
        if (accounts.size > 0) {
            // TODO: prompt asking which account to use
            val account = accounts[0]
            val future = accountManager.getAuthToken(account, AUTHTOKEN_TYPE, null, this, null, null)

            Thread(Runnable {
                try {
                    val bundle = future.result

                    val authtoken = bundle.getString(AccountManager.KEY_AUTHTOKEN)
                    println(if (authtoken != null) "SUCCESS!\ntoken: " + authtoken else "FAIL")
                    Log.d("udinic", "GetToken Bundle is " + bundle)
                } catch (e: Exception) {
                    e.printStackTrace()
                    println(e.message)
                }
            }).start()
        } else {
            accountManager.addAccount(ACCOUNT_TYPE, AUTHTOKEN_TYPE, null, null, this, AccountManagerCallback {
                fun run(future: AccountManagerFuture<Bundle>) {
                    val bundle = future.result
                }
            }, null);
        }
//        login()
    }

    fun login() {
        val intent = Intent(this, AuthenticatorActivity::class.java)
        startActivity(intent)
    }
}
