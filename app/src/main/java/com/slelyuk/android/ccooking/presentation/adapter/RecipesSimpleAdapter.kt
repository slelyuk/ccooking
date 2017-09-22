package com.slelyuk.android.ccooking.presentation.adapter

import android.databinding.ViewDataBinding
import com.github.salomonbrys.kodein.conf.KodeinGlobalAware
import com.github.salomonbrys.kodein.instance
import com.google.firebase.storage.StorageReference
import com.slelyuk.android.ccooking.R
import com.slelyuk.android.ccooking.data.RecipeClickEvent
import com.slelyuk.android.ccooking.data.RecipeData
import com.slelyuk.android.ccooking.databinding.BindingHolder
import com.slelyuk.android.ccooking.databinding.ViewModelAdapter
import com.slelyuk.android.ccooking.misc.logger.L
import com.slelyuk.android.ccooking.presentation.viewmodel.RecipeViewModel
import org.greenrobot.eventbus.EventBus

class RecipesSimpleAdapter(
    data: List<RecipeData> = listOf()) : ViewModelAdapter<RecipeViewModel, RecipeData>(
    data), KodeinGlobalAware {

  private val bus: EventBus = instance()
  private val imagesRef: StorageReference = instance("images")

  override val layoutId: Int = R.layout.item_recipe

  // we do not need to spam objects. use 1 listener per data set
  private val clickListener: (Int) -> Unit = {
    L.e { "OnRecipeClickEvent: ${data[it].id}" }
    bus.post(RecipeClickEvent(data[it]))
  }

  override fun onFillViewModel(holder: BindingHolder<ViewDataBinding, RecipeViewModel>,
      viewModel: RecipeViewModel, item: RecipeData, position: Int) {
    viewModel.name.set(item.n)
    viewModel.description.set(item.d)
    viewModel.timeTotal.set("${item.t?.c}")

    val im = item.im!!
    val imgUrl = im.substring(im.lastIndexOf('/') + 1, im.length)
    viewModel.image.set(imagesRef.child(imgUrl))

    viewModel.currentPosition = holder.adapterPosition
    if (viewModel.onClickDelegate == null)
      viewModel.onClickDelegate = clickListener

  }

}