package com.slelyuk.android.ccooking.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.slelyuk.android.ccooking.R
import com.slelyuk.android.ccooking.data.RecipeClickEvent
import com.slelyuk.android.ccooking.databinding.ActivityReceiptsMainBinding
import com.slelyuk.android.ccooking.misc.BundleBuilder
import com.slelyuk.android.ccooking.misc.logger.L
import com.slelyuk.android.ccooking.misc.runActivity
import com.slelyuk.android.ccooking.presentation.BaseActivity
import com.slelyuk.android.ccooking.presentation.MainView
import com.slelyuk.android.ccooking.presentation.viewModelOf
import com.slelyuk.android.ccooking.presentation.viewmodel.RecipesViewModel
import org.greenrobot.eventbus.Subscribe

/**
 * Main receipts activity.
 *
 * Created by slelyuk on 9/4/17.
 */
class RecipesActivity : BaseActivity<ActivityReceiptsMainBinding, RecipesViewModel>(), MainView {

  override fun provideViewModel(): RecipesViewModel = viewModelOf()

  override val layoutId: Int = R.layout.activity_receipts_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun showLogoutDialog(callback: () -> Unit) {

  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.receipts_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_logout -> viewModel.onLogoutClick(this)
    }
    return super.onOptionsItemSelected(item)
  }

  @Subscribe
  fun onRecipeClickEvent(e: RecipeClickEvent) {
    L.d { "OnRecipeClickEvent: ${e.recipe.id}" }
    runActivity<RecipeDetailsActivity> {"recipeId"..e.recipe.id}
  }
}