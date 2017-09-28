package com.slelyuk.android.ccooking

import android.app.Application
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.google.firebase.database.FirebaseDatabase
import com.slelyuk.android.ccooking.di.setupGlobalKodein
import com.slelyuk.android.ccooking.misc.logger.CrashReportingTree
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Android application class.
 *
 * Created by slelyuk on 9/3/17.
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()

    setupGlobalKodein()

    when {
      BuildConfig.DEBUG -> Timber.plant(DebugTree())
      else -> Timber.plant(CrashReportingTree())
    }

    FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    //  registering kodein’s lifecycle manager to enable the auto activity scope to work
    registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
  }

}