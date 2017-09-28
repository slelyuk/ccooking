package com.slelyuk.android.ccooking.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder
import com.slelyuk.android.ccooking.App
import com.slelyuk.android.ccooking.data.ResourceProvider
import org.greenrobot.eventbus.EventBus

/**
 * Created by slelyuk on 9/28/17.
 */
fun App.setupGlobalKodein() = with(Kodein.global) {
  this.addConfig {
    bind() from instance(applicationContext)
  }
  addImport(toolsModule)
  addImport(authModule)
  addImport(databaseModule)
  addImport(storageModule)
}

val toolsModule = Kodein.Module {
  bind() from singleton { ResourceProvider(instance()) }
  bind() from singleton { EventBus.builder().build() }
  bind() from singleton {
    GsonBuilder()
        .setPrettyPrinting()
        .create()
  }
}

val databaseModule = Kodein.Module {
  val db = FirebaseDatabase.getInstance()
  bind(KODEIN_TAG_RECIPES) from singleton { db.getReference(FIREBASE_REF_RECIPES) }
  bind(KODEIN_TAG_INGREDIENTS) from singleton { db.getReference(FIREBASE_REF_INGREDIENTS) }
  bind(KODEIN_TAG_STAGES) from singleton { db.getReference(FIREBASE_REF_STAGES) }
}

val storageModule = Kodein.Module {
  val storage = FirebaseStorage.getInstance()
  bind(KODEIN_TAG_IMAGES) from singleton { storage.getReference(FIREBASE_REF_IMAGES) }
}

val authModule = Kodein.Module {
  bind() from singleton { FirebaseAuth.getInstance() }
}

const val FIREBASE_REF_LOCALE = "en"

const val KODEIN_TAG_RECIPES = "recipes"
const val KODEIN_TAG_INGREDIENTS = "ingredients"
const val KODEIN_TAG_STAGES = "stages"
const val KODEIN_TAG_IMAGES = "images"

const val FIREBASE_REF_RECIPES = "$FIREBASE_REF_LOCALE/$KODEIN_TAG_RECIPES"
const val FIREBASE_REF_INGREDIENTS = "$FIREBASE_REF_LOCALE/$KODEIN_TAG_INGREDIENTS"
const val FIREBASE_REF_STAGES = "$FIREBASE_REF_LOCALE/$KODEIN_TAG_STAGES"

const val FIREBASE_REF_IMAGES = "recipe-header-image"