package com.slelyuk.android.ccooking.presentation

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.github.salomonbrys.kodein.conf.KodeinGlobalAware
import com.github.salomonbrys.kodein.instance
import com.slelyuk.android.ccooking.data.ResourceProvider
import com.slelyuk.android.ccooking.databinding.ReliableField
import com.slelyuk.android.ccooking.exception.AuthException
import com.slelyuk.android.ccooking.exception.AppException
import com.slelyuk.android.ccooking.misc.logger.L
import com.slelyuk.android.ccooking.presentation.viewmodel.ToolbarViewModel
import kotlinx.coroutines.experimental.CancellationException
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch


abstract class BaseViewModel : ViewModel(), KodeinGlobalAware, Progressable {

  protected val resources: ResourceProvider = instance()

  protected var lifecycleJob = Job()

  val toolbar = ToolbarViewModel()
  val showProgress = ObservableBoolean(true)

  // commands
  val finishCommand = Command<Unit>()
  val startLoginScreenCommand = Command<Unit>()
  val showSnackbarCommand = SnackBarCommand()
  val showErrorCommand = Command<AppException>()


  override fun onCleared() {
    super.onCleared()
    lifecycleJob.cancel(CancellationException("viewModel destroyed"))
  }

  override fun toggleProgress(enabled: Boolean) {
    showProgress.set(enabled)
  }

  open fun onLoggedIn(success: Boolean) {}

  /**
   * Convenient helper
   */
  fun async(showErrors: Boolean = true, block: suspend CoroutineScope.() -> Unit) {
    if (lifecycleJob.isCompleted) {
      lifecycleJob = Job()
    }

    toggleProgress(true)

    launch(UI + lifecycleJob, block = block).invokeOnCompletion { e ->
      toggleProgress(false)

      if (e != null) {
        e.printStackTrace()
        if (showErrors) {
          when (e) {
            is AppException -> showErrorCommand(e)
            is AuthException -> {
              showSnackbarCommand("Authentication Failed")
              startLoginScreenCommand()
            }
            else -> throw e
          }
        } else
          throw e
      }
    }
  }


}