package com.slelyuk.android.ccooking.presentation.viewmodel

import android.databinding.ObservableField
import android.view.View
import com.google.firebase.storage.StorageReference

/**
 * Created by slelyuk on 9/10/17.
 */
class RecipeViewModel {
  val name = ObservableField<String>()
  val description = ObservableField<String>()
  val timeTotal = ObservableField<String>()
  val image = ObservableField<StorageReference>()
  val vegeterian = ObservableField<Boolean>()

  var onClickDelegate: ((Int, View) -> Unit)? = null
  var currentPosition = -1

  fun onClick(v: View) {
    onClickDelegate?.invoke(currentPosition, v)
  }
}