package com.slelyuk.android.ccooking.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.slelyuk.android.ccooking.R.layout
import com.slelyuk.android.ccooking.databinding.ActivityReceiptsDetailsBinding
import com.slelyuk.android.ccooking.misc.arg
import com.slelyuk.android.ccooking.presentation.BaseActivity
import com.slelyuk.android.ccooking.presentation.DetailsView
import com.slelyuk.android.ccooking.presentation.viewModelOf
import com.slelyuk.android.ccooking.presentation.viewmodel.RecipeDetailsViewModel

/**
 * Details receipt activity.
 *
 * Created by slelyuk on 9/4/17.
 */
class RecipeDetailsActivity : BaseActivity<ActivityReceiptsDetailsBinding, RecipeDetailsViewModel>(), DetailsView {

  override fun provideViewModel(): RecipeDetailsViewModel = viewModelOf()

  override val layoutId: Int = layout.activity_receipts_details

  private val recipeId: String? by arg()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportPostponeEnterTransition()

    viewModel.recipeId.set(recipeId)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    //menuInflater.inflate(R.menu.receipts_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    //when (item.itemId) {
    //  id.action_logout -> viewModel.onLogoutClick(this)
    //}
    return super.onOptionsItemSelected(item)
  }

  override fun toggleBookmark(callback: () -> Unit) {
    TODO("not implemented")
  }

  override fun createShoppingList(callback: () -> Unit) {
    TODO("not implemented")
  }

  override fun addNote(callback: () -> Unit) {
    TODO("not implemented")
  }
}