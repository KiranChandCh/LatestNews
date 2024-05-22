package com.ckc.latestnews.viewmodel

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> ComponentActivity.viewModelProvider(): T {
    return ViewModelProvider(this)[T::class.java]
}