package com.slelyuk.android.ccooking.databinding

import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.databinding.Observable
import android.support.annotation.IdRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import com.slelyuk.android.ccooking.misc.InfiniteScrollListener
import com.slelyuk.android.ccooking.misc.logger.L
import com.slelyuk.android.ccooking.presentation.viewmodel.RecipesViewModel
import com.yalantis.filter.adapter.FilterAdapter
import com.yalantis.filter.model.FilterModel
import com.yalantis.filter.widget.Filter

fun BaseObservable.addOnPropertyChangedCallback(function: () -> Unit) {
  addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
    override fun onPropertyChanged(o: Observable, id: Int) {
      function()
    }
  })
}

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean?) {
  view.visibility = if (visible == null || visible) View.VISIBLE else View.GONE
}

@BindingAdapter("isRefreshing")
fun setIsRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
  view.isRefreshing = isRefreshing
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, ref: StorageReference) {
  Glide.with(view).load(ref).into(view)
}

@BindingAdapter("paginationListener")
fun setPaginationListener(view: RecyclerView, viewModel: RecipesViewModel) {
  view.clearOnScrollListeners()
  view.addOnScrollListener(
      InfiniteScrollListener({ viewModel.loadNext() }, view.layoutManager as LinearLayoutManager))
}

@BindingAdapter("focusCommand")
fun focusCommand(v: View, @IdRes id: Int) {
  v.findViewById<View>(id)?.requestFocus()
}

@BindingAdapter("filterAdapter")
fun <T: FilterModel> setAdapter(view: Filter<T>, a: FilterAdapter<T>) {
  view.adapter = a
  view.noSelectedItemText = "All"
  view.build()
}



