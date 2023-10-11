package com.mediacard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mediacard.adapters.MediaCardAdapter
import com.mediacard.models.PlayerProps

class MediaCardCarousel @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val recyclerView: RecyclerView
  private var mediaCardAdapter: MediaCardAdapter? = null

  init {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.media_carousel_view, this, true)
    recyclerView = view.findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    // Add the PagerSnapHelper
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // Find the position of the item that is now in the center of the screen
        val centerPosition = (recyclerView.layoutManager as LinearLayoutManager)
          .findFirstVisibleItemPosition()

        // Preload videos around this position
        mediaCardAdapter?.preloadVideosAroundPosition(centerPosition)
      }
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          resetAdjacentVideos()
        }
      }
    })

  }

  private fun resetAdjacentVideos() {
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    val visiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()

    if (visiblePosition != RecyclerView.NO_POSITION) {
      // Reset the video on the left
      if (visiblePosition > 0) {
        mediaCardAdapter?.resetVideoAtPosition(recyclerView, visiblePosition - 1)
      }

      // Reset the video on the right
      if (visiblePosition < mediaCardAdapter?.itemCount ?: 0 - 1) {
        mediaCardAdapter?.resetVideoAtPosition(recyclerView, visiblePosition + 1)
      }
    }
  }

  fun setPlayerProps(playerPropsList: List<PlayerProps>) {
    val adapter = MediaCardAdapter(context, playerPropsList)
    mediaCardAdapter = adapter
    recyclerView.adapter = adapter
  }

}

