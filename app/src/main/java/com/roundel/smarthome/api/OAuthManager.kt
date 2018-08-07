package com.roundel.smarthome.api

import java.net.ConnectException

class OAuthManager(val tokens: TokenPair) {
    @Throws(OAuthException::class, ConnectException::class)
    constructor(username: String, password: String) : this(obtainTokensFromPasswordGrant(username, password))

    companion object {
        @Throws(OAuthException::class, ConnectException::class)
        private fun obtainTokensFromPasswordGrant(username: String, password: String): TokenPair {
            val call = getOauthService().getTokenByPassword(username, password, "2", "2225d0b42a0da298085a3b5f48c8a629")

            val response = call.execute()
            if (!response.isSuccessful) {
                throw ConnectException()
            }
            return response.body() ?: throw OAuthException("Response body is null")
        }

        //TODO: Save the tokens for later use within the app
    }
}