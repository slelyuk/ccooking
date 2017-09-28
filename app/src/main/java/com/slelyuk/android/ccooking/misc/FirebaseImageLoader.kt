package com.slelyuk.android.ccooking.misc

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DataSource.LOCAL
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.data.DataFetcher.DataCallback
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StreamDownloadTask
import com.slelyuk.android.ccooking.misc.logger.L
import java.io.IOException
import java.io.InputStream

/**
 * Created by slelyuk on 9/19/17.
 */
class FirebaseImageLoader : ModelLoader<StorageReference, InputStream> {

  override fun buildLoadData(model: StorageReference, width: Int, height: Int,
      options: Options?): LoadData<InputStream> {
    return LoadData(ObjectKey(model), FirebaseStorageFetcher(model))
  }

  override fun handles(model: StorageReference?): Boolean {
    return true
  }

  private inner class FirebaseStorageFetcher internal constructor(
      private val mRef: StorageReference) : DataFetcher<InputStream> {

    override fun loadData(priority: Priority?, callback: DataCallback<in InputStream>?) {
      mStreamTask = mRef.stream
      try {
        mInputStream = Tasks.await(mStreamTask!!).stream
        callback?.onDataReady(mInputStream)
      } catch (err: IOException) {
        callback?.onLoadFailed(err)
      }
    }

    override fun getDataClass(): Class<InputStream> {
      return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
      return LOCAL
    }

    private var mStreamTask: StreamDownloadTask? = null
    private var mInputStream: InputStream? = null

    override fun cleanup() {
      if (mInputStream != null) {
        try {
          mInputStream?.close()
          mInputStream = null
        } catch (e: IOException) {
          L.w { e }
        }

      }
    }

    override fun cancel() {
      mStreamTask?.isInProgress ?: mStreamTask?.cancel()
    }
  }

  class Factory : ModelLoaderFactory<StorageReference, InputStream> {
    override fun build(
        multiFactory: MultiModelLoaderFactory?): ModelLoader<StorageReference, InputStream> {
      return FirebaseImageLoader()
    }

    override fun teardown() {

    }

  }
}

