package com.slelyuk.android.ccooking.presentation.adapter

import android.databinding.ViewDataBinding
import android.view.View
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
import java.lang.ref.WeakReference

class RecipesSimpleAdapter(
    data: List<RecipeData> = listOf()) : ViewModelAdapter<RecipeViewModel, RecipeData>(
    data), KodeinGlobalAware {

  private val bus: EventBus = instance()
  private val imagesRef: StorageReference = instance("images")

  override val layoutId: Int = R.layout.item_recipe

  private val clickListener: (Int, View) -> Unit = { pos: Int, v: View ->
    L.e { "OnRecipeClickEvent: ${data[pos].id}" }
    /*bus.post(RecipeClickEvent(data[pos],
        arrayOf(
            WeakReference(v.findViewById(R.id.icon)),
            WeakReference(v.findViewById(R.id.tv_name)),
            WeakReference(v.findViewById(R.id.tv_description))
        )
    ))*/
    bus.post(RecipeClickEvent(data[pos], arrayOf(WeakReference(v.findViewById(R.id.icon)))))
  }

  override fun onFillViewModel(holder: BindingHolder<ViewDataBinding, RecipeViewModel>,
      viewModel: RecipeViewModel, item: RecipeData, position: Int) {
    viewModel.name.set(item.n)
    viewModel.description.set(item.d)
    viewModel.timeTotal.set("${item.t?.c}")

    viewModel.image.set(imagesRef.child(item.im))

    viewModel.currentPosition = holder.adapterPosition
    if (viewModel.onClickDelegate == null)
      viewModel.onClickDelegate = clickListener
  }

}