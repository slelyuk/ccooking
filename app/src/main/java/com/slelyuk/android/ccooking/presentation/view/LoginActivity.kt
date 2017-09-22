package com.slelyuk.android.ccooking.presentation.view

import android.app.Activity
import android.os.Bundle
import com.slelyuk.android.ccooking.R
import com.slelyuk.android.ccooking.databinding.ActivityLoginBinding
import com.slelyuk.android.ccooking.presentation.BaseActivity
import com.slelyuk.android.ccooking.presentation.LoginView
import com.slelyuk.android.ccooking.presentation.viewModelOf
import com.slelyuk.android.ccooking.presentation.viewmodel.LoginViewModel


/**
 * A login screen.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginView {

  override fun provideViewModel(): LoginViewModel = viewModelOf()

  override val layoutId: Int = R.layout.activity_login

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun finishOk() {
    setResult(Activity.RESULT_OK)
    finish()
  }
}
