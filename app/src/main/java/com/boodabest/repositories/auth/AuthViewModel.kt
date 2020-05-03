package com.boodabest.repositories.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    private val _isAuth = MutableLiveData(false)

    val isAuth: LiveData<Boolean>
        get() = _isAuth
}