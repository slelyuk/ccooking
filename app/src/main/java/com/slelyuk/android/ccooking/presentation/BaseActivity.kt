package com.slelyuk.android.ccooking.presentation

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.github.salomonbrys.kodein.conf.KodeinGlobalAware
import com.github.salomonbrys.kodein.instance
import com.slelyuk.android.ccooking.BR
import com.slelyuk.android.ccooking.R
import com.slelyuk.android.ccooking.data.AuthRequiredEvent
import com.slelyuk.android.ccooking.exception.AppException
import com.slelyuk.android.ccooking.misc.runActivityForResult
import com.slelyuk.android.ccooking.misc.toast
import com.slelyuk.android.ccooking.presentation.view.LoginActivity
import com.slelyuk.android.ccooking.presentation.view.REQUEST_LOGIN
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), BaseView, KodeinGlobalAware {

  private val bus: EventBus = instance()

  abstract val layoutId: Int

  @Suppress("LeakingThis")
  private val registry = LifecycleRegistry(this)

  override fun getLifecycle(): LifecycleRegistry = registry

  lateinit var viewModel: VM
  lateinit var binding: B

  open protected val bindImmediately = false
  open protected val hasUpButton = false

  abstract fun provideViewModel(): VM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    bindView()

    observeData()

    setupToolbar()
  }

  private fun bindView() {
    binding = DataBindingUtil.setContentView(this, layoutId)
    viewModel = provideViewModel()
    binding.setVariable(BR.viewModel, viewModel)
    binding.setVariable(BR.view, this)

    if (bindImmediately)
      binding.executePendingBindings()
  }

  /**
   * Instantiate ViewModel here
   */
  protected open fun observeData() {
    viewModel.showSnackbarCommand.observe(this) {
      showSnackbar(it.text)
    }
    viewModel.showErrorCommand.observe(this) {
      showError(it)
    }

    viewModel.finishCommand.observe(this) {
      finish()
    }
    viewModel.startLoginScreenCommand.observe(this) {
      onAuthRequiredEvent(null)
    }
  }

  override fun onStart() {
    super.onStart()
    bus.register(this)
  }

  override fun onStop() {
    super.onStop()
    bus.unregister(this)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
  }

  private fun setupToolbar() {
    val toolbar: Toolbar? = findViewById(R.id.toolbar)
    if (toolbar != null) {
      setSupportActionBar(toolbar)
      supportActionBar?.setDisplayHomeAsUpEnabled(hasUpButton)
      onToolbarCreated(toolbar)
    }
  }

  protected fun showSnackbar(
      text: String,
      callback: (() -> Unit)? = null,
      duration: Int = Snackbar.LENGTH_LONG,
      @StringRes actionText: Int = 0,
      actionCallback: (() -> Unit)? = null
  ) {
    val content: View = findViewById(R.id.coordinator) ?: findViewById(android.R.id.content)
    val s = Snackbar.make(content, text, duration)
    if (callback != null) {
      val snackCallback = object : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
          callback()
        }
      }
      s.addCallback(snackCallback)
    }
    if (actionCallback != null)
      s.setAction(actionText, {
        actionCallback()
      })
    s.show()
  }

  protected fun showError(e: AppException) {
    showSnackbar(e.message ?: "Unknown Error")
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> finish()
    }
    return super.onOptionsItemSelected(item)
  }


  protected open fun onToolbarCreated(toolbar: Toolbar) {}

  @Subscribe
  fun onErrorEvent(e: AppException) {
    showError(e)
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  open fun onAuthRequiredEvent(e: AuthRequiredEvent?) {
    toast(getString(R.string.auth_required))
    runActivityForResult<LoginActivity>(requestCode = REQUEST_LOGIN)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_LOGIN) {
      viewModel.onLoggedIn(resultCode == Activity.RESULT_OK)
    }
  }
}