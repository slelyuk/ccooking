package com.slelyuk.android.ccooking.presentation.adapter

import android.content.Context
import android.graphics.Color
import com.github.salomonbrys.kodein.conf.KodeinGlobalAware
import com.github.salomonbrys.kodein.instance
import com.yalantis.filter.adapter.FilterAdapter
import com.yalantis.filter.model.FilterModel
import com.yalantis.filter.widget.FilterItem


/**
 * Created by slelyuk on 9/20/17.
 */
class RecipesFilterAdapter(items: List<String>) : FilterAdapter<FilterModel>(items.map { Model(it) }),
    KodeinGlobalAware {

  private val appContext: Context = instance()

  override fun createView(position: Int, item: FilterModel): FilterItem {
    val filterItem = FilterItem(appContext)

    filterItem.strokeColor = Color.GRAY
    filterItem.textColor = Color.WHITE
    filterItem.checkedTextColor = Color.WHITE
    filterItem.color = Color.LTGRAY
    filterItem.checkedColor = Color.DKGRAY
    filterItem.text = item.getText()
    filterItem.deselect()

    return filterItem
  }

  internal class Model(val value: String): FilterModel {

    override fun getText(): String {
      return value
    }

  }
}