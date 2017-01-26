package com.goweiwen.ivleandroid.authenticator

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils

/**
 * Created by weiwen on 1/25/17.
 */

val AUTHTOKEN_TYPE = "com.goweiwen.ivle"
val ACCOUNT_TYPE = "com.goweiwen.ivle"

class Authenticator(context: Context) : AbstractAccountAuthenticator(context) {
    var context: Context
    init {
        this.context = context
    }

    override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?): Bundle? {
        return null
    }

    override fun getAuthTokenLabel(authTokenType: String?): String? {
        return null
    }

    override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle? {
        return null
    }

    override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account, authTokenType: String?, options: Bundle?): Bundle {
        // TODO: implement

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals(AUTHTOKEN_TYPE)) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType")
            return result
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        val am = AccountManager.get(context)
        val authToken = am.getPassword(account)
        if (authToken != null) {
            if (!TextUtils.isEmpty(authToken)) {
                val result = Bundle()
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, SyncStateContract.Constants.ACCOUNT_TYPE)
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
                return result
            }
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity panel.
        val intent = Intent(context, AuthenticatorActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle

    }

    override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?): Bundle {
        val result = Bundle()
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false)
        return result
    }

    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?): Bundle {
        val intent = Intent(context, AuthenticatorActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

}
