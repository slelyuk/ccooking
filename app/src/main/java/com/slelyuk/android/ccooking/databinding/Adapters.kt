package com.slelyuk.android.ccooking.databinding

import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.databinding.Observable
import android.graphics.drawable.Drawable
import android.support.annotation.IdRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.StorageReference
import com.slelyuk.android.ccooking.misc.InfiniteScrollListener
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
fun loadImage(view: ImageView, ref: StorageReference?) {
  if (ref == null) return

  Glide.with(view).load(ref)
      .listener(
          object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                isFirstResource: Boolean): Boolean {
              scheduleStartPostponedTransition(view)
              return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?,
                target: Target<Drawable>?, dataSource: DataSource?,
                isFirstResource: Boolean): Boolean {
              scheduleStartPostponedTransition(view)
              return false
            }
          }
      )
      .into(view)
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
fun <T : FilterModel> setAdapter(view: Filter<T>, a: FilterAdapter<T>) {
  view.adapter = a
  view.noSelectedItemText = "All"
  view.build()
}

/**
 * Schedules the shared element transition to be started immediately
 * after the shared element has been measured and laid out within the
 * activity's view hierarchy. Some common places where it might make
 * sense to call this method are:
 *
 * (1) Inside a Fragment's onCreateView() method (if the shared element
 * lives inside a Fragment hosted by the called Activity).
 *
 * (2) Inside a Picasso Callback object (if you need to wait for Picasso to
 * asynchronously load/scale a bitmap before the transition can begin).
 */
private fun scheduleStartPostponedTransition(sharedElement: View) {
  sharedElement.viewTreeObserver.addOnPreDrawListener(
      object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
          sharedElement.viewTreeObserver.removeOnPreDrawListener(this)
          (sharedElement.context as AppCompatActivity).supportStartPostponedEnterTransition()
          return true
        }
      })
}


