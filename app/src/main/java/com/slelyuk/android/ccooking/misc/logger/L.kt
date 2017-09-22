package com.slelyuk.android.ccooking.misc.logger

import com.slelyuk.android.ccooking.misc.toStringSafe
import timber.log.Timber


/**
 * Logger wrapper.
 *
 * Created by slelyuk on 9/4/17.
 */
object L {

  /**
   * Log an debug message with optional exception.
   */
  inline fun d(t: Throwable? = null, msg: () -> Any?) {
    Timber.d(t, msg.toStringSafe())
  }

  /**
   * Log an info message with optional exception.
   */
  inline fun i(t: Throwable? = null, msg: () -> Any?) {
    Timber.i(t, msg.toStringSafe())
  }

  /**
   * Log an warning message with optional exception.
   */
  inline fun w(t: Throwable? = null, msg: () -> Any?) {
    Timber.w(t, msg.toStringSafe())
  }

  /**
   * Log an error message with optional exception.
   */
  inline fun e(t: Throwable? = null, msg: () -> Any?) {
    Timber.e(t, msg.toStringSafe())
  }
}