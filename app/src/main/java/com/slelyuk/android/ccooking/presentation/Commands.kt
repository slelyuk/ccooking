package com.slelyuk.android.ccooking.presentation

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import com.slelyuk.android.ccooking.presentation.SnackBarCommand.Params


class SnackBarCommand : Command<Params>() {

  operator fun invoke(text: String,
      callback: (() -> Unit)? = null,
      duration: Int = Snackbar.LENGTH_LONG,
      @StringRes actionText: Int = 0,
      actionCallback: (() -> Unit)? = null) {
    value = Params(text, callback, duration, actionText, actionCallback)
  }

  class Params(
      val text: String,
      val callback: (() -> Unit)? = null,
      val duration: Int = Snackbar.LENGTH_LONG,
      @StringRes val actionText: Int = 0,
      val actionCallback: (() -> Unit)? = null
  )
}
