package com.slelyuk.android.ccooking.data

/**
 * Recipe model entity.
 */
data class RecipeData(
    var id: String? = null,
    var n: String? = null,
    var d: String? = null,
    var im: String = "",
    var v: Boolean? = null,
    var t: TimeInfo? = null) {

  @Suppress("unused")
  constructor() : this(null, null, null, "", null)

  /**
   * TimeInfo model entity.
   */
  data class TimeInfo(
      var c: String = "",
      var cM: Int = 0,
      var p: String = "",
      var pM: Int = 0) {

    @Suppress("unused")
    constructor() : this("", 0, "", 0)
  }
}