package com.mediacard

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget

class MediaCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val videoView: VideoView
    private val playPauseButton: ImageButton
    private val seekBar: SeekBar
    private val opacityBackground: View
    private val hideControlsHandler = Handler(Looper.getMainLooper())
    private val hideControlsRunnable = Runnable { hideControls() }
    private val updateSeekBarRunnable = Runnable { updateSeekBar() }
    private var currentMediaUrl: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.media_card_view, this, true)

        imageView = findViewById(R.id.imageView)
        videoView = findViewById(R.id.videoView)
        playPauseButton = findViewById(R.id.playPauseButton)
        seekBar = findViewById(R.id.seekBar)
        opacityBackground = findViewById(R.id.opacityBackground)

        videoView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (playPauseButton.visibility == VISIBLE) {
                    hideControls()
                } else {
                    showControls()
                }
            }
            true
        }

        playPauseButton.setOnClickListener { togglePlayPause() }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    videoView.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: Do something when user starts moving the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional: Do something when user stops moving the SeekBar
            }
        })
    }

    fun setPlayerProps(mediaUrl: String, placeholderUrl: String) {
        currentMediaUrl = mediaUrl

        if (mediaUrl.endsWith(".mp4") || mediaUrl.endsWith(".mov")) {
            imageView.visibility = VISIBLE
            videoView.visibility = VISIBLE
            showControls()

            Glide.with(context)
                .load(placeholderUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)

            videoView.setVideoURI(Uri.parse(mediaUrl))
            videoView.setOnPreparedListener {
              imageView.visibility = INVISIBLE
              seekBar.max = videoView.duration
              updateSeekBar()
            }
            videoView.setOnErrorListener { _, what, extra ->
                Log.e("VideoView", "Error loading video: $what, $extra")
                true
            }
            videoView.start()

        } else {
            imageView.visibility = VISIBLE
            Glide.with(context)
                .load(mediaUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

    private fun showControls() {
        playPauseButton.visibility = VISIBLE
        seekBar.visibility = VISIBLE
        opacityBackground.visibility = VISIBLE
        hideControlsHandler.postDelayed(hideControlsRunnable, 5000)
    }

    private fun hideControls() {
        playPauseButton.visibility = GONE
        seekBar.visibility = GONE
        opacityBackground.visibility = GONE
        hideControlsHandler.removeCallbacks(hideControlsRunnable)
    }

    fun setLoopCount(loopCount: Int) {
        if (!currentMediaUrl?.endsWith(".mp4")!! && !currentMediaUrl?.endsWith(".mov")!!) {
            Glide.with(context)
                .load(currentMediaUrl)
                .into(object : ImageViewTarget<Drawable>(imageView) {
                    override fun setResource(resource: Drawable?) {
                        if (resource is GifDrawable) {
                            resource.setLoopCount(loopCount)
                        }
                        imageView.setImageDrawable(resource)
                    }
                })
        }
    }

    private fun togglePlayPause() {
        if (videoView.isPlaying) {
            videoView.pause()
            playPauseButton.setImageResource(R.drawable.ic_play)
        } else {
            videoView.start()
            playPauseButton.setImageResource(R.drawable.ic_pause)
        }
    }

    fun resetVideo() {
      Log.e("HEEHEHE", "SISIISI")
      videoView.stopPlayback()
    }


  private fun updateSeekBar() {
        seekBar.progress = videoView.currentPosition
        hideControlsHandler.postDelayed(updateSeekBarRunnable, 1000)
    }
}
