package ru.customelectronics.test.repository

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import ru.customelectronics.adsscreen.retrofit.RetrofitInstance
import ru.customelectronics.test.retrofit.GifResponse
import ru.customelectronics.test.retrofit.Result

object ServerRepository {

    fun getRandomGif(): Call<GifResponse> {
        return RetrofitInstance.api.getRandomGif()
    }

    fun getLatestGif(num: Int): Call<Result> {
        return RetrofitInstance.api.getLatestGif(num)
    }

    fun getTopGif(num: Int): Call<Result> {
        return RetrofitInstance.api.getTopGif(num)
    }
}