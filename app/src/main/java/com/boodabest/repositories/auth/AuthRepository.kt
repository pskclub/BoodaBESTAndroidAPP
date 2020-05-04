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

    fun fetchMe(login: LoginResponse): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<User>> {
                return authService.profile(login.accessToken)
            }

            override fun loadFromDb() = userDao.get()

            override fun saveCallResult(item: User) {
                val newUser = item.copy(
                    accessToken = login.accessToken,
                    accessTokenExpire = login.accessTokenExpire,
                    refreshToken = login.refreshToken,
                    refreshTokenExpire = login.refreshTokenExpire
                )
                userDao.delete()
                userDao.insert(newUser)
            }

            override fun shouldFetch(data: User?) = true
        }.asLiveData()
    }


    fun me(): LiveData<User> {
        return userDao.get()
    }

    fun logout() {
        appExecutors.diskIO().execute {
            userDao.delete()
        }
    }
}