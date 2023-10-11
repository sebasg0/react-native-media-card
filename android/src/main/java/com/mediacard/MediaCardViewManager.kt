package com.mediacard

import android.graphics.Color
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

@ReactModule(name = MediaCardViewManager.NAME)
class MediaCardViewManager : MediaCardViewManagerSpec<MediaCardView>() {
    override fun getName(): String {
        return NAME
    }

    public override fun createViewInstance(context: ThemedReactContext): MediaCardView {
        return MediaCardView(context)
    }

  @ReactProp(name = "playerProps")
  override fun setPlayerProps(view: MediaCardView?, playerProps: ReadableMap) {
    val property1 = playerProps.getString("mediaUrl")
    val property2 = playerProps.getString("placeHolderImage")
    view?.setPlayerProps(property1 ?: "", property2 ?: "")
    // Now use property1 and property2 in your native code
  }

  @ReactProp(name = "loopCount")
    override fun setLoopCount(view: MediaCardView?, loopCount: Int?) {
      // If loopCount doesn't come from JS, default to 1
      view?.setLoopCount(loopCount ?: 1)
    }

    companion object {
        const val NAME = "MediaCardView"
    }
}
