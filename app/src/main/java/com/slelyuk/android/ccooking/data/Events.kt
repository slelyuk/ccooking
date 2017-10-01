package com.slelyuk.android.ccooking.data

import android.view.View
import java.lang.ref.WeakReference

class AuthRequiredEvent

class RecipeClickEvent(val item: RecipeData, val targetRef: Array<WeakReference<out View>>)
class IngredientClickEvent(val item: String)
class ServingTypeClickEvent(val item: ServingType)