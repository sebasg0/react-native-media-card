package com.mediacard

import android.view.View
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager

abstract class MediaCardCarouselManagerSpec<T : View> : SimpleViewManager<T>() {
  abstract fun setPlayerProps(view: T?, value: ReadableMap)
  abstract fun setLoopCount(view: T?, value: Int?)
}
