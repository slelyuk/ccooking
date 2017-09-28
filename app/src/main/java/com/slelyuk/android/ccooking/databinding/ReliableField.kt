package com.slelyuk.android.ccooking.databinding

import android.databinding.ObservableField

class ReliableField<T>(private var value: T) : ObservableField<T>(value) {

  override fun get(): T {
    return value
  }

  override fun set(value: T) {
    this.value = value
    notifyChange()
  }

}