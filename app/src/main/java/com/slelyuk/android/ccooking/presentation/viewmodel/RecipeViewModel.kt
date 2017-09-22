package com.slelyuk.android.ccooking.presentation.viewmodel

import android.databinding.ObservableField
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

  var onClickDelegate:((Int)->Unit)? = null
  var currentPosition = -1

  fun onClick() {
    onClickDelegate?.invoke(currentPosition)
  }
}