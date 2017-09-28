package com.slelyuk.android.ccooking.data

/**
 * Created by slelyuk on 9/16/17.
 */
data class IngridientData(
    val rId: String?,
    val i: List<ServingType>?
) {
  constructor() : this(null, null)
}

data class ServingType(
    val t: String?,
    val v: List<String>?
) {
  constructor() : this(null, null)
}