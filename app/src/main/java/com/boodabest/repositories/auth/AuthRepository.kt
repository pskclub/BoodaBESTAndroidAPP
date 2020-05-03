package com.boodabest.repositories.auth

import androidx.lifecycle.LiveData
import com.boodabest.core.AppExecutors
import com.boodabest.database.User
import com.boodabest.database.UserDao
import com.boodabest.models.LoginResponse
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.NetworkBoundResourceNoCache
import com.boodabest.network.Resource
import com.boodabest.services.AuthService
import com.boodabest.services.LoginBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val authService: AuthService,
    private val userDao: UserDao
) {

    fun login(username: String, password: String): LiveData<Resource<LoginResponse>> {
        return object : NetworkBoundResourceNoCache<LoginResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<LoginResponse>> {
                return authService.login(LoginBody(username, password))
            }
        }.asLiveData()
    }

  /*  fun get(username: String, password: String): LiveData<Resource<LoginResponse>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<User>> {
                return authService.login(LoginBody(username, password))
            }
        }.asLiveData()
    }*/
}