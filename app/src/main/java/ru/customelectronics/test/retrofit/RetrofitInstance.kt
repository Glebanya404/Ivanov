package ru.customelectronics.adsscreen.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.customelectronics.test.retrofit.Api
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    const val PRIMARY_URL = "https://developerslife.ru/"

    private val client = OkHttpClient.Builder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
    }.build()



    private var retrofit = Retrofit.Builder()
        .baseUrl(PRIMARY_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    var api: Api = retrofit.create(Api::class.java)
}