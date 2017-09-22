package com.slelyuk.android.ccooking.presentation

import android.view.View.OnClickListener
import com.github.salomonbrys.kodein.conf.KodeinGlobalAware

interface BaseView : KodeinGlobalAware {
  fun finish()
}

interface MainView : BaseView {
  fun showLogoutDialog(callback: () -> Unit)
}

interface DetailsView : BaseView {
  fun toggleBookmark(callback: () -> Unit)
  fun createShoppingList(callback: () -> Unit)
  fun addNote(callback: () -> Unit)
}

interface LoginView : BaseView {
  fun finishOk()
}