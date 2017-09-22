package com.slelyuk.android.ccooking.presentation.viewmodel

import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.slelyuk.android.ccooking.exception.AppException
import com.slelyuk.android.ccooking.presentation.BaseViewModel
import com.slelyuk.android.ccooking.presentation.LoginView

/**
 * LoginViewModel.
 *
 * Created by slelyuk on 9/4/17.
 */
class LoginViewModel : BaseViewModel() {

  private val firebaseAuth: FirebaseAuth = instance()

  fun onLoginClick(view: LoginView) = async {
    firebaseAuth.signInAnonymously().addOnCompleteListener { authResult ->
      if (authResult.isSuccessful) {
        view.finishOk()
        showSnackbarCommand(authResult.result.user.uid)
      } else {
        showErrorCommand(AppException(authResult.exception?.message))
      }
    }
  }

}