package com.slelyuk.android.ccooking.presentation.viewmodel

import com.github.salomonbrys.kodein.instance
import com.google.firebase.database.DatabaseReference
import com.slelyuk.android.ccooking.R.string
import com.slelyuk.android.ccooking.databinding.ReliableField
import com.slelyuk.android.ccooking.databinding.addOnPropertyChangedCallback
import com.slelyuk.android.ccooking.presentation.BaseViewModel

/**
 * RecipeDetailsViewModel.
 *
 * Created by slelyuk on 9/4/17.
 */
class RecipeDetailsViewModel : BaseViewModel() {
  private val recipesRef: DatabaseReference = instance("recipes")
  private val ingredientsRef: DatabaseReference = instance("ingredients")
  private val stagesRef: DatabaseReference = instance("stages")

  val recipeId: ReliableField<String?> = ReliableField(null)

  init {
    toolbar.title.set(resources.getString(string.title_recipe))
    initUI()
  }

  private fun initUI() {
    recipeId.addOnPropertyChangedCallback { async { loadData(recipeId.get()) } }
  }

  suspend private fun loadData(id: String?) {
    // TODO Fetch recipe, ingredients, stages
  }

  override fun onCleared() {
    super.onCleared()
  }
}