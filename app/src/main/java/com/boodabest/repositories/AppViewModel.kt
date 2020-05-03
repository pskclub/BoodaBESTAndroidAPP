package com.boodabest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AppViewModel @Inject constructor() : ViewModel() {
    private val _title = MutableLiveData<String>()
    private val _backAble = MutableLiveData(false)
    val title: LiveData<String>
        get() = _title
    val backAble: LiveData<Boolean>
        get() = _backAble

    fun updateTitle(title: String) = _title.postValue(title)
    fun updateBackAble(backAble: Boolean) = _backAble.postValue(backAble)
}