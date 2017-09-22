package com.slelyuk.android.ccooking.misc

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.firebase.storage.StorageReference
import java.io.InputStream

/**
 * Created by slelyuk on 9/19/17.
 */
@GlideModule
class AppGlide : AppGlideModule() {

  override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
    registry?.prepend(StorageReference::class.java, InputStream::class.java,
        FirebaseImageLoader.Factory())
  }

}