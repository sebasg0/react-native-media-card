package com.mediacard

import android.graphics.Color
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

@ReactModule(name = MediaCardViewManager.NAME)
class MediaCardViewManager :
  MediaCardViewManagerSpec<MediaCardView>() {
  override fun getName(): String {
    return NAME
  }

  public override fun createViewInstance(context: ThemedReactContext): MediaCardView {
    return MediaCardView(context)
  }

  @ReactProp(name = "color")
  override fun setColor(view: MediaCardView?, color: String?) {
    view?.setBackgroundColor(Color.parseColor(color))
  }

  companion object {
    const val NAME = "MediaCardView"
  }
}
