package com.margelo.nitro.dpimetric

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import com.facebook.proguard.annotations.DoNotStrip
import com.margelo.nitro.NitroModules
import android.os.Build
import android.view.WindowManager
import android.util.DisplayMetrics
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import android.view.WindowMetrics
import kotlin.math.pow
import kotlin.math.sqrt

@DoNotStrip
class NitroDpiMetric : HybridNitroDpiMetricSpec() {
  private val context = NitroModules.applicationContext ?: throw Exception("Context is null")

  @SuppressLint("ObsoleteSdkInt")
  override fun deviceInch(): Double {
    val dm = DisplayMetrics()
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val widthPixels: Int
    val heightPixels: Int
    val xdpi: Float
    val ydpi: Float

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      val metrics: WindowMetrics = windowManager.currentWindowMetrics
      val bounds = metrics.bounds
      widthPixels = bounds.width()
      heightPixels = bounds.height()

      val displayMetrics = context.resources.displayMetrics
      xdpi = displayMetrics.xdpi
      ydpi = displayMetrics.ydpi
    } else {
      @Suppress("DEPRECATION")
      windowManager.defaultDisplay.getRealMetrics(dm)
      widthPixels = dm.widthPixels
      heightPixels = dm.heightPixels
      xdpi = dm.xdpi
      ydpi = dm.ydpi
    }

    val x = (widthPixels / xdpi.toDouble()).pow(2.0)
    val y = (heightPixels / ydpi.toDouble()).pow(2.0)
    val screenInches = sqrt(x + y)

    return screenInches
  }

  override fun getDpi(): Double {
    val dm: DisplayMetrics = context.resources.displayMetrics
    return dm.densityDpi.toDouble()
  }

  override fun getNavBarHeight(): Double {
    val resources = context.resources
    val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

    if (!hasMenuKey && !hasBackKey) {
      val orientation = resources.configuration.orientation

      val resourceId = resources.getIdentifier(
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
          "navigation_bar_height"
        else
          "navigation_bar_height_landscape",
        "dimen",
        "android"
      )

      if (resourceId > 0) {
        return resources.getDimensionPixelSize(resourceId).toDouble()
      }
    }
    return 0.0
  }

  override fun isTablet(): Boolean {
    val configuration = context.resources.configuration
    return (configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
            || configuration.smallestScreenWidthDp >= 600
  }
}
