package com.goweiwen.ivleandroid.authenticator

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by weiwen on 1/25/17.
 */
class AuthenticationService : Service() {
    private lateinit var authenticator: Authenticator

    override fun onCreate() {
        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder = authenticator.iBinder
}