package com.roundel.smarthome.api

import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import java.util.*


class TokenPair(@SerializedName("access_token") val accessToken: String,
                @SerializedName("refresh_token") val refreshToken: String,
                val expires: Date) {
    companion object {
        val deserializer = JsonDeserializer<TokenPair> { json, _, _ ->
            val jsonObject = json.asJsonObject ?: throw JsonParseException("Invalid json")

            val date = Date()
            date.time = date.time + jsonObject["expires"].asLong
            TokenPair(jsonObject["access_token"].asString,
                    jsonObject["refresh_token"].asString,
                    date)
        }
    }
}