package com.slelyuk.android.ccooking.data

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat

/**
 * Resources abstraction. allows easier mocking when testing
 */
class ResourceProvider(val context: Context) {

  fun getString(@StringRes id: Int): String = context.getString(id)
  fun getString(@StringRes id: Int, vararg args: Any): String = context.getString(id, *args)

  fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)
}