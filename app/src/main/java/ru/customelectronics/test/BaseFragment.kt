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
import ru.customelectronics.test.retrofit.GifResponse

abstract class BaseFragment : Fragment() {
    private val TAG = javaClass.canonicalName

    val postList = ArrayList<GifResponse>()
    private var postPosition = 0
    private val handler = Handler()
    private var state = State.LOADED

    enum class State{
        LOADED,
        LOADING_PREVIEW,
        LOADING_GIF,
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getNewGif()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment, container, false)
        view.fragment__next_button.setOnClickListener {
            if(state != State.LOADING_PREVIEW) {
                postPosition++
                view.fragment__prev_button.isEnabled = true
                if (postPosition >= postList.size) {
                    getNewGif()
                } else {
                    downloadGif()
                }
            }

        }

        view.fragment__prev_button.apply {
            if(state != State.LOADING_PREVIEW) {
                isEnabled = false
                setOnClickListener {
                    postPosition--
                    downloadGif()
                    isEnabled = postPosition != 0
                }
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        if(postList.isNotEmpty()){
            downloadGif()
        }
    }

    abstract fun getNewGif()

    fun downloadGif() {
        val gif = postList[postPosition]
        downloadGifPreview(gif)
    }

    private fun downloadGifPreview(gif: GifResponse) {
        state = State.LOADING_PREVIEW
        val background = view?.fragment__imageView?.drawable
        Glide.with(this)
            .load(gif.previewURL)
            .placeholder(background)
            .error(R.drawable.ic_failed)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    setFailed(gif)
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
        state = State.LOADING_GIF
        val background = view?.fragment__imageView?.drawable
        Glide.with(this)
            .load(gif.gifURL)
            .placeholder(background)
            .error(R.drawable.ic_failed)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    setFailed(gif)
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
                    state = State.LOADED
                    return false
                }

            })
            .into(view?.fragment__imageView!!)
    }

    private fun setFailed(gif: GifResponse) {
        state = State.LOADED
        view?.fragment__progressBar?.visibility = View.INVISIBLE
        view?.fragment__gifTitle?.text = "Failed"
        postList.remove(gif)
        postPosition--
    }
}