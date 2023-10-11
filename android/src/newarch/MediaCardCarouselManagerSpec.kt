package com.mediacard

import android.view.View

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.viewmanagers.MediaCardCarouselManagerDelegate
import com.facebook.react.viewmanagers.MediaCardCarouselManagerInterface
import com.facebook.soloader.SoLoader

abstract class MediaCardCarouselManagerSpec<T : View> : SimpleViewManager<T>(), MediaCardCarouselManagerInterface<T> {
  private val mDelegate: ViewManagerDelegate<T>

  init {
    mDelegate = MediaCardCarouselManagerDelegate(this)
  }

  override fun getDelegate(): ViewManagerDelegate<T>? {
    return mDelegate
  }

  companion object {
    init {
      if (BuildConfig.CODEGEN_MODULE_REGISTRATION != null) {
        SoLoader.loadLibrary(BuildConfig.CODEGEN_MODULE_REGISTRATION)
      }
    }
  }
}
