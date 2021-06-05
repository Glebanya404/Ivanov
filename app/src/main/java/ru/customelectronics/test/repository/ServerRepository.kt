package ru.customelectronics.test.repository

import retrofit2.Call
import retrofit2.Response
import ru.customelectronics.adsscreen.retrofit.RetrofitInstance
import ru.customelectronics.test.retrofit.GifResponse

object ServerRepository {

    fun getRandomGif(): Call<GifResponse> {
        return RetrofitInstance.api.getRandomGif()
    }
}