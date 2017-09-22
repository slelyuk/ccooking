package com.slelyuk.android.ccooking.presentation.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableField

class ToolbarViewModel : BaseObservable() {
    val title = ObservableField<String>()
    val subtitle = ObservableField<String>()
}