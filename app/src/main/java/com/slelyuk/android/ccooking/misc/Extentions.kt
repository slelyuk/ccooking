package com.slelyuk.android.ccooking.misc

import android.content.Context
import android.os.Looper
import android.widget.Toast

/**
 * Utils.
 *
 * Created by slelyuk on 9/4/17.
 */

@Suppress("NOTHING_TO_INLINE")
inline fun (() -> Any?).toStringSafe(): String {
  return try {
    invoke().toString()
  } catch (e: Exception) {
    "Log message invocation failed: $e"
  }
}

fun Context.toast(text: CharSequence) = Toast.makeText(this.applicationContext, text,
    Toast.LENGTH_SHORT).show()

val isUiThread: Boolean get() = Thread.currentThread() === Looper.getMainLooper().thread