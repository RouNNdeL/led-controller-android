package com.roundel.smarthome.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


interface OAuthService {
    @FormUrlEncoded
    @POST("/oauth/token")
    fun getTokenByPassword(@Field("username") username: String,
                           @Field("password") password: String,
                           @Field("client_id") clientId: String,
                           @Field("client_secret") clientSecret: String,
                           @Field("scope") scope: String = "home_control",
                           @Field("grant_type") grant_type: String = "password"):
            Call<TokenPair>
}

fun getOauthService(): OAuthService {
    val retrofit = Retrofit.Builder()
            .baseUrl("https://dev.zdul.xyz")
            .addConverterFactory(
                    GsonConverterFactory.create(
                            GsonBuilder().registerTypeAdapter(
                                    TokenPair::class.java, TokenPair.deserializer).create()))
            .build()

    return retrofit.create(OAuthService::class.java)

}