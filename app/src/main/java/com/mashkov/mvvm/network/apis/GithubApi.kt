package com.mashkov.mvvm.network.apis

import com.mashkov.mvvm.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("/users")
    suspend fun getUsers(): List<User>

    @GET("/users/{user}")
    suspend fun getUser(@Path("user") user: String): User
}