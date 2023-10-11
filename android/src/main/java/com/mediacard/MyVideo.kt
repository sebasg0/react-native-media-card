package com.mediacard

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView


class MyVideoView : VideoView {
  private var mVideoWidth = 0
  private var mVideoHeight = 0

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
    context,
    attrs,
    defStyle
  )

  constructor(context: Context?) : super(context)

  fun setVideoSize(width: Int, height: Int) {
    mVideoWidth = width
    mVideoHeight = height
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    // Log.i("@@@", "onMeasure");
    var width = getDefaultSize(mVideoWidth, widthMeasureSpec)
    var height = getDefaultSize(mVideoHeight, heightMeasureSpec)
    if (mVideoWidth > 0 && mVideoHeight > 0) {
      if (mVideoWidth * height > width * mVideoHeight) {
        // Log.i("@@@", "image too tall, correcting");
        height = width * mVideoHeight / mVideoWidth
      } else if (mVideoWidth * height < width * mVideoHeight) {
        // Log.i("@@@", "image too wide, correcting");
        width = height * mVideoWidth / mVideoHeight
      } else {
        // Log.i("@@@", "aspect ratio is correct: " +
        // width+"/"+height+"="+
        // mVideoWidth+"/"+mVideoHeight);
      }
    }
    // Log.i("@@@", "setting size: " + width + 'x' + height);
    setMeasuredDimension(width, height)
  }
}
