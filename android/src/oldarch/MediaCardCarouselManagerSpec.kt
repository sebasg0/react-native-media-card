package com.mediacard

import android.view.View
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager

abstract class MediaCardCarouselManagerSpec<T : View> : SimpleViewManager<T>() {
  abstract fun setData(view: T?, value: ReadableArray?)
}
