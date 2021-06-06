package ru.customelectronics.test

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.customelectronics.test.repository.ServerRepository
import ru.customelectronics.test.retrofit.GifResponse

class RandomFragment : BaseFragment() {
    override fun getNewGif() {
        ServerRepository.getRandomGif().enqueue(object : Callback<GifResponse> {
            override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                response.body()?.let { it ->
                    postList.add(it)
                    downloadGif()
                }
            }

            override fun onFailure(call: Call<GifResponse>, t: Throwable) {

            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RandomFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}