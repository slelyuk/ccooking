package com.slelyuk.android.ccooking.presentation.adapter

import android.databinding.ViewDataBinding
import android.view.View
import com.github.salomonbrys.kodein.conf.KodeinGlobalAware
import com.github.salomonbrys.kodein.instance
import com.slelyuk.android.ccooking.R.layout
import com.slelyuk.android.ccooking.data.ServingType
import com.slelyuk.android.ccooking.data.ServingTypeClickEvent
import com.slelyuk.android.ccooking.databinding.BindingHolder
import com.slelyuk.android.ccooking.databinding.ViewModelAdapter
import com.slelyuk.android.ccooking.presentation.viewmodel.ServingTypeViewModel
import org.greenrobot.eventbus.EventBus

class ServingTypesAdapter(
    data: List<ServingType> = listOf()) : ViewModelAdapter<ServingTypeViewModel, ServingType>(
    data), KodeinGlobalAware {

  private val bus: EventBus = instance()

  override val layoutId: Int = layout.item_serving_type

  private val clickListener: (Int, View) -> Unit = { pos: Int, v: View ->
    bus.post(ServingTypeClickEvent(data[pos]))
  }

  override fun onFillViewModel(holder: BindingHolder<ViewDataBinding, ServingTypeViewModel>,
      viewModel: ServingTypeViewModel, item: ServingType, position: Int) {
    viewModel.servingName.set(item.t)
    viewModel.ingredientsAdapter.set(IngredientsAdapter(item.v ?: listOf()))

    viewModel.currentPosition = holder.adapterPosition
    if (viewModel.onClickDelegate == null)
      viewModel.onClickDelegate = clickListener
  }

}