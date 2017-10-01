package com.slelyuk.android.ccooking.presentation.viewmodel

import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View

class IngredientViewModel {
  val name = ObservableField<String>()
  val checked = ObservableField<Boolean>()

  var onClickDelegate: ((Int, View) -> Unit)? = null
  var currentPosition = -1

  fun onClick(v: View) {
    onClickDelegate?.invoke(currentPosition, v)
  }
}