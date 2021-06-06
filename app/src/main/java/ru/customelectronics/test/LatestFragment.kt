package ru.customelectronics.test

import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.customelectronics.test.repository.ServerRepository
import ru.customelectronics.test.retrofit.GifResponse
import ru.customelectronics.test.retrofit.Result

class LatestFragment: BaseFragment() {
    val TAG = javaClass.canonicalName
    var page = 0

    override fun getNewGif() {
        ServerRepository.getLatestGif(page).enqueue(object : Callback<Result> {
            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {
                response.body()?.let {
                    page++
                    for (gif in it.result){
                        postList.add(gif)
                    }
                    downloadGif()
                }

            }

            override fun onFailure(call: Call<Result>, t: Throwable) {

            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LatestFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}