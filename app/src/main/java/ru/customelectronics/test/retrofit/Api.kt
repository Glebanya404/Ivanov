package ru.customelectronics.test.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("random?json=true")
    fun getRandomGif(): Call<GifResponse>
}