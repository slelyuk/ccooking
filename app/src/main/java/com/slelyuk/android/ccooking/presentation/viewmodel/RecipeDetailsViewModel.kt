package com.slelyuk.android.ccooking.presentation.viewmodel

import android.databinding.ObservableField
import com.github.salomonbrys.kodein.instance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.slelyuk.android.ccooking.R.string
import com.slelyuk.android.ccooking.data.IngridientData
import com.slelyuk.android.ccooking.data.RecipeData
import com.slelyuk.android.ccooking.data.StageData
import com.slelyuk.android.ccooking.databinding.addOnPropertyChangedCallback
import com.slelyuk.android.ccooking.di.KODEIN_TAG_IMAGES
import com.slelyuk.android.ccooking.di.KODEIN_TAG_INGREDIENTS
import com.slelyuk.android.ccooking.di.KODEIN_TAG_RECIPES
import com.slelyuk.android.ccooking.di.KODEIN_TAG_STAGES
import com.slelyuk.android.ccooking.misc.logger.L
import com.slelyuk.android.ccooking.presentation.BaseViewModel

/**
 * RecipeDetailsViewModel.
 *
 * Created by slelyuk on 9/4/17.
 */
class RecipeDetailsViewModel : BaseViewModel() {
  private val recipesRef: DatabaseReference = instance(KODEIN_TAG_RECIPES)
  private val ingredientsRef: DatabaseReference = instance(KODEIN_TAG_INGREDIENTS)
  private val stagesRef: DatabaseReference = instance(KODEIN_TAG_STAGES)
  private val imagesRef: StorageReference = instance(KODEIN_TAG_IMAGES)

  val recipeId: ObservableField<String?> = ObservableField()
  val image: ObservableField<StorageReference> = ObservableField()
  val name = ObservableField<String>()
  val description = ObservableField<String>()
  val ingredients = ObservableField<String>()
  val stages = ObservableField<String>()

  init {
    toolbar.title.set(resources.getString(string.title_recipe))
    initUI()
  }

  private fun initUI() {
    recipeId.addOnPropertyChangedCallback { async { loadData(recipeId.get()) } }
  }

  suspend private fun loadData(id: String?) {
    recipesRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(e: DatabaseError?) {
        L.e(e?.toException(), { e?.message })
      }

      override fun onDataChange(s: DataSnapshot?) {
        val recipe = s?.getValue(RecipeData::class.java) ?: return

        name.set(recipe.n)
        description.set(recipe.d)
        image.set(imagesRef.child(recipe.im))
      }
    })

    ingredientsRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(e: DatabaseError?) {
        L.e(e?.toException(), { e?.message })
      }

      override fun onDataChange(s: DataSnapshot?) {
        val ingreds = s?.getValue(IngridientData::class.java) ?: return
        ingredients.set(ingreds.toString())
      }
    })

    stagesRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(e: DatabaseError?) {
        L.e(e?.toException(), { e?.message })
      }

      override fun onDataChange(s: DataSnapshot?) {
        val s = s?.getValue(StageData::class.java) ?: return
        stages.set(s.toString())
      }
    })
  }

  override fun onCleared() {
    super.onCleared()
  }
}