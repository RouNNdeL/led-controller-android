package com.roundel.smarthome.api

import java.io.IOError
import java.io.IOException
import java.net.ConnectException

class OAuthManager(val tokens: TokenPair) {
    @Throws(OAuthException::class, IOException::class)
    constructor(username: String, password: String) : this(obtainTokensFromPasswordGrant(username, password))

    companion object {
        @Throws(OAuthException::class, IOException::class)
        private fun obtainTokensFromPasswordGrant(username: String, password: String): TokenPair {
            val call = getOauthService().getTokenByPassword(username, password, "2", "2225d0b42a0da298085a3b5f48c8a629")

            val response = call.execute()
            if (!response.isSuccessful) {
                throw OAuthException("Response not successful")
            }
            return response.body() ?: throw OAuthException("Response body is null")
        }

        //TODO: Save the tokens for later use within the app
    }
}