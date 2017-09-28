package com.slelyuk.android.ccooking.presentation.view

import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.view.Menu
import android.view.MenuItem
import com.slelyuk.android.ccooking.R
import com.slelyuk.android.ccooking.data.RecipeClickEvent
import com.slelyuk.android.ccooking.databinding.ActivityReceiptsMainBinding
import com.slelyuk.android.ccooking.misc.logger.L
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

    val intent = Intent(this, RecipeDetailsActivity::class.java)
    intent.putExtra(EXTRA_RECIPE_ID, e.recipe.id)

    /*val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this,
        android.support.v4.util.Pair<View, String>(e.targetRef[0].get()!!, getString(R.string.transition_recipe_photo)),
        android.support.v4.util.Pair<View, String>(e.targetRef[1].get()!!, getString(R.string.transition_recipe_title)),
        android.support.v4.util.Pair<View, String>(e.targetRef[2].get()!!, getString(R.string.transition_recipe_description))
    )*/

    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this, e.targetRef[0].get(), getString(R.string.transition_recipe_photo)
    )
    startActivity(intent, options.toBundle())
  }
}