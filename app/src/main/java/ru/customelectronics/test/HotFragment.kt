package ru.customelectronics.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_hot.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.customelectronics.test.repository.ServerRepository
import ru.customelectronics.test.retrofit.GifResponse

class HotFragment : Fragment() {
    private val TAG = javaClass.canonicalName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hot, container, false)
        view.fragment__next_button.setOnClickListener {
            Log.d(TAG, "Next button clicked")
            ServerRepository.getRandomGif().enqueue(object : Callback<GifResponse>{
                override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                    Glide.with(this@HotFragment)
                        .load(response.body()?.gifURL)
//                        .centerCrop()
                        .into(view.fragment__imageView)
                }

                override fun onFailure(call: Call<GifResponse>, t: Throwable) {

                }

            })
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HotFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}