package com.mediacard

import android.graphics.Color
import android.util.Log
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.mediacard.models.PlayerProps

@ReactModule(name = MediaCardCarouselManager.NAME)
class MediaCardCarouselManager : MediaCardCarouselManagerSpec<MediaCardCarousel>() {
    override fun getName(): String {
        return NAME
    }

    public override fun createViewInstance(context: ThemedReactContext): MediaCardCarousel {
        return MediaCardCarousel(context)
    }

  @ReactProp(name = "data")
    override fun setData(view: MediaCardCarousel?, data: ReadableArray?) {
      val playerPropsList: MutableList<PlayerProps> = mutableListOf()

      data?.let {
        for (i in 0 until it.size()) {
          Log.e("nooo", "aaaaa : $it")
          val playerPropsMap = it.getMap(i)
          val mediaUrl = playerPropsMap?.getString("mediaUrl")
          val placeHolderImage = playerPropsMap?.getString("placeHolderImage")
          val playerProps = PlayerProps(mediaUrl, placeHolderImage)
          playerPropsList.add(playerProps)
        }
      }
      Log.e("nooo", "aaaaa : $playerPropsList")
      view?.setPlayerProps(playerPropsList)
    }

    companion object {
        const val NAME = "MediaCardCarousel"
    }
}
