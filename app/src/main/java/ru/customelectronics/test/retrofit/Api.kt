package ru.customelectronics.test.retrofit

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("random?json=true")
    fun getRandomGif(): Call<GifResponse>

    @GET("latest/{num}?json=true")
    fun getLatestGif(@Path("num") num: Int): Call<Result>

    @GET("top/{num}?json=true")
    fun getTopGif(@Path("num") num: Int): Call<Result>
}