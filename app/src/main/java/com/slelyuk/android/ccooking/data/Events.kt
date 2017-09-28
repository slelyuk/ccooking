package com.slelyuk.android.ccooking.data

import android.view.View
import java.lang.ref.WeakReference

class AuthRequiredEvent

class RecipeClickEvent(val recipe: RecipeData, val targetRef: Array<WeakReference<out View>>)