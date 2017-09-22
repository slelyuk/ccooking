package com.slelyuk.android.ccooking

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder
import com.slelyuk.android.ccooking.data.ResourceProvider
import com.slelyuk.android.ccooking.misc.logger.CrashReportingTree
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Android application class.
 *
 * Created by slelyuk on 9/3/17.
 */
class App : Application() {

  fun setupGlobalKodein() = with(Kodein.global) {
    this.addConfig {
      bind() from instance(applicationContext)

      import(toolsModule)
      import(authModule)
      import(databaseModule)
      import(storageModule)
    }
  }

  val toolsModule = Kodein.Module {
    bind() from singleton { EventBus.builder().build() }
    bind() from singleton { ResourceProvider(this@App) }
    bind() from singleton { this@App.assets }

    bind() from singleton {
      GsonBuilder()
          .setPrettyPrinting()
          .create()
    }
  }

  val databaseModule = Kodein.Module {
    bind("recipes") from singleton { FirebaseDatabase.getInstance().getReference("en/recipes") }
    bind("ingredients") from singleton { FirebaseDatabase.getInstance().getReference("en/ingredients") }
    bind("stages") from singleton { FirebaseDatabase.getInstance().getReference("en/stages") }
  }

  val storageModule = Kodein.Module {
    bind("images") from singleton { FirebaseStorage.getInstance().getReference("recipe-header-image") }
  }

  val authModule = Kodein.Module {
    bind() from singleton { FirebaseAuth.getInstance() }
  }

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