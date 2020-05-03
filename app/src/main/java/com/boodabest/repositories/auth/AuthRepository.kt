package com.boodabest.repositories.auth

import com.boodabest.core.AppExecutors
import com.boodabest.database.UserDao
import com.boodabest.services.AuthService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val authService: AuthService,
    private val userDao: UserDao
) {

}