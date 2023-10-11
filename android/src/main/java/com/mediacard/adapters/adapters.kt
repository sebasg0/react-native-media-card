package com.mediacard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediacard.MediaCardView
import com.mediacard.R
import com.mediacard.models.PlayerProps
import kotlin.math.max
import android.media.MediaPlayer
import android.net.Uri
import kotlin.math.min

class MediaCardAdapter(
  private val context: Context,
  private val playerPropsList: List<PlayerProps>
) : RecyclerView.Adapter<MediaCardAdapter.MediaCardViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCardViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.media_carousel_card, parent, false)
    return MediaCardViewHolder(view)
  }

  override fun onBindViewHolder(holder: MediaCardViewHolder, position: Int) {
    val playerProps = playerPropsList[position]
    holder.mediaCardView.resetVideo()
    holder.mediaCardView.setPlayerProps(
      playerProps.mediaUrl ?: "",
      playerProps.placeHolderImage ?: ""
    )

    // Preload videos around the current position
    preloadVideosAroundPosition(position)
  }

  fun resetVideoAtPosition(recyclerView: RecyclerView, position: Int) {
    val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as? MediaCardViewHolder
    viewHolder?.mediaCardView?.resetVideo()
  }

  override fun onViewRecycled(holder: MediaCardViewHolder) {
    super.onViewRecycled(holder)
    holder.mediaCardView.resetVideo()
  }


  override fun getItemCount(): Int = playerPropsList.size

  class MediaCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mediaCardView: MediaCardView = view as MediaCardView
  }

  public fun preloadVideosAroundPosition(position: Int) {
    val preloadRange = 1  // Define how many videos to preload on each side of the current video

    // Calculate the range of items to preload
    val start = max(0, position - preloadRange)
    val end = min(playerPropsList.size - 1, position + preloadRange)

    // Preload videos in the calculated range
    for (preloadPosition in start..end) {
      // Check if this position is valid and if we need to preload this video
      if (preloadPosition in playerPropsList.indices && preloadPosition != position) {
        preloadVideo(playerPropsList[preloadPosition])
      }
    }
  }

  private fun preloadVideo(playerProps: PlayerProps) {
    val mediaPlayer = MediaPlayer()

    try {
      // Set the data source of the MediaPlayer
      mediaPlayer.setDataSource(context, Uri.parse(playerProps.mediaUrl))

      // Prepare the MediaPlayer asynchronously to avoid blocking the main thread
      mediaPlayer.prepareAsync()

      // Set a listener to release the MediaPlayer once it's prepared
      mediaPlayer.setOnPreparedListener {
        // Optionally, you might start and pause the player to ensure that the
        // first frame is loaded and ready to display
        it.start()
        it.pause()

        // Release or cache the MediaPlayer for future use
        it.release()
      }
    } catch (e: Exception) {
      // Handle exceptions, such as an IOException, here
      e.printStackTrace()

      // Ensure the MediaPlayer is released in case of an exception
      mediaPlayer.release()
    }
  }
}


