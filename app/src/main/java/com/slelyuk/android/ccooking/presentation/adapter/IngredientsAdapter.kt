package com.slelyuk.android.ccooking.presentation.adapter

import android.databinding.ViewDataBinding
import android.view.View
import com.github.salomonbrys.kodein.conf.KodeinGlobalAware
import com.github.salomonbrys.kodein.instance
import com.slelyuk.android.ccooking.R.layout
import com.slelyuk.android.ccooking.data.IngredientClickEvent
import com.slelyuk.android.ccooking.data.ServingType
import com.slelyuk.android.ccooking.data.ServingTypeClickEvent
import com.slelyuk.android.ccooking.databinding.BindingHolder
import com.slelyuk.android.ccooking.databinding.ViewModelAdapter
import com.slelyuk.android.ccooking.presentation.viewmodel.IngredientViewModel
import com.slelyuk.android.ccooking.presentation.viewmodel.ServingTypeViewModel
import org.greenrobot.eventbus.EventBus

class IngredientsAdapter(
    data: List<String> = listOf()) : ViewModelAdapter<IngredientViewModel, String>(
    data), KodeinGlobalAware {

  private val bus: EventBus = instance()

  override val layoutId: Int = layout.item_ingredient

  private val clickListener: (Int, View) -> Unit = { pos: Int, v: View ->
    bus.post(IngredientClickEvent(data[pos]))
  }

  override fun onFillViewModel(holder: BindingHolder<ViewDataBinding, IngredientViewModel>,
      viewModel: IngredientViewModel, item: String, position: Int) {
    viewModel.name.set("- $item")

    viewModel.currentPosition = holder.adapterPosition
    if (viewModel.onClickDelegate == null)
      viewModel.onClickDelegate = clickListener
  }

}