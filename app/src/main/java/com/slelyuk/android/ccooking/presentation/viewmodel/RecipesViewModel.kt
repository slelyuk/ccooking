package com.slelyuk.android.ccooking.presentation.viewmodel

import android.databinding.ObservableField
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.slelyuk.android.ccooking.R
import com.slelyuk.android.ccooking.data.RecipeData
import com.slelyuk.android.ccooking.di.KODEIN_TAG_RECIPES
import com.slelyuk.android.ccooking.exception.AppException
import com.slelyuk.android.ccooking.misc.logger.L
import com.slelyuk.android.ccooking.presentation.BaseViewModel
import com.slelyuk.android.ccooking.presentation.MainView
import com.slelyuk.android.ccooking.presentation.adapter.RecipesSimpleAdapter
import com.slelyuk.android.ccooking.presentation.invoke


/**
 * ReceiptsViewModel.
 *
 * Created by slelyuk on 9/4/17.
 */
class RecipesViewModel : BaseViewModel() {

  private val databaseRef: DatabaseReference = instance(KODEIN_TAG_RECIPES)
  private val auth: FirebaseAuth = instance()

  val adapter = ObservableField<RecipesSimpleAdapter>()

  private val recipesList = mutableListOf<RecipeData>()

  init {
    toolbar.title.set(resources.getString(R.string.title_recipes))
    initUI()
  }

  private fun initUI() {
    auth.addAuthStateListener { a ->
      if (a.currentUser == null) {
        startLoginScreenCommand()
      }
    }

    adapter.set(RecipesSimpleAdapter(recipesList))
    loadNext()
  }

  fun onLogoutClick(view: MainView) {
    async { auth.signOut() }
  }


  fun loadNext() {
    if (recipesList.isEmpty()) {
      async {
        loadRecipes(if (recipesList.isNotEmpty()) recipesList.last().id else null, 2)
      }
    }
  }

  // TODO DELETE
  suspend fun loadRecipes(lastId: String?, pageSize: Int) {
    databaseRef
        .limitToLast(100)
        .addListenerForSingleValueEvent(
            object : ValueEventListener {
              override fun onCancelled(error: DatabaseError?) {
                showErrorCommand(AppException(error?.message + "\n" + error?.details))
                L.e { error?.message + "\n" + error?.details }
              }

              override fun onDataChange(snapshot: DataSnapshot?) {
                showSnackbarCommand("Loaded count: ${snapshot?.childrenCount}")

                val s: HashMap<String, RecipeData>? = snapshot?.getValue(
                    object : GenericTypeIndicator<HashMap<String, RecipeData>>() {})

                if (s?.values == null)
                  return

                recipesList.addAll(s.values)
                adapter.get().notifyDataSetChanged()
              }
            })
  }

  override fun onCleared() {
    super.onCleared()
    recipesList.clear()
  }
}