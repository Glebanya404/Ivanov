package ru.customelectronics.test

import android.content.Context
import android.graphics.drawable.Drawable
import android.opengl.Visibility
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
import kotlinx.android.synthetic.main.fragment_hot.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.customelectronics.test.repository.ServerRepository
import ru.customelectronics.test.retrofit.GifResponse

class HotFragment : Fragment() {
    private val TAG = javaClass.canonicalName

    private val postList = ArrayList<GifResponse>()
    private var postPosition = -1
    private val handler = Handler()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getNewGif()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hot, container, false)
        view.fragment__next_button.setOnClickListener {
            getNewGif()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        if(postList.isNotEmpty()){
            downloadGif()
        }
    }

    private fun getNewGif() {
        ServerRepository.getRandomGif().enqueue(object : Callback<GifResponse> {
            override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                response.body()?.let { it ->
                    postList.add(it)
                    postPosition++
                    downloadGif()
                }
            }

            override fun onFailure(call: Call<GifResponse>, t: Throwable) {

            }
        })
    }

    private fun downloadGif() {
        val gif = postList[postPosition]
        downloadGifPreview(gif)
    }

    private fun downloadGifPreview(gif: GifResponse) {
        val background = view?.fragment__imageView?.drawable
        Glide.with(this)
            .load(gif.previewURL)
            .placeholder(background)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    view?.fragment__progressBar?.visibility = View.VISIBLE
                    view?.fragment__gifTitle?.text = gif.description
                    handler.post { downloadGifBody(gif) }
                    return false
                }

            })
            .into(view?.fragment__imageView!!)
    }

    private fun downloadGifBody(gif: GifResponse) {
        val background = view?.fragment__imageView?.drawable
        Glide.with(this)
            .load(gif.gifURL)
            .placeholder(background)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    view?.fragment__progressBar?.visibility = View.INVISIBLE
                    view?.fragment__gifTitle?.text = "Failed"
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    view?.fragment__progressBar?.visibility = View.INVISIBLE
                    return false
                }

            })
            .into(view?.fragment__imageView!!)
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