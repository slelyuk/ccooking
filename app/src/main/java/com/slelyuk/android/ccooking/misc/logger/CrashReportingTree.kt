package com.slelyuk.android.ccooking.misc.logger

import android.util.Log
import timber.log.Timber

/**
 * A tree which logs important information for crash reporting.
 *
 * Created by slelyuk on 9/4/17.
 */
class CrashReportingTree : Timber.Tree() {
  override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG) {
      return
    }

    //FakeCrashLibrary.log(priority, tag, message)

    if (t != null) {
      if (priority == Log.ERROR) {
        //FakeCrashLibrary.logError(t)
      } else if (priority == Log.WARN) {
        //FakeCrashLibrary.logWarning(t)
      }
    }
  }
}