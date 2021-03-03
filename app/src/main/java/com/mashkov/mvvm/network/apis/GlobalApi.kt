package com.mashkov.mvvm.network.apis

object GlobalApi : ApiProvider() {

    fun initialize() {
        initHttpClient()
    }

    override fun isInitialized() = isOkHttpClientInitialized

    val github: GithubApi
        get() = getApi(GithubApi::class.java)

}
