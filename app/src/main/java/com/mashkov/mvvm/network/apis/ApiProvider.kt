package com.mashkov.mvvm.network.apis

import com.mashkov.mvvm.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class ApiProvider {

    private lateinit var okHttpClient: OkHttpClient

    private val apis: MutableMap<Class<*>, Any> = HashMap()

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    abstract fun isInitialized(): Boolean

    @Suppress("UNCHECKED_CAST")
    fun <T> getApi(apiType: Class<T>): T {
        check(isInitialized())
        val cachedApi = apis[apiType]
        return if (cachedApi != null) {
            cachedApi as T
        } else {
            val api = retrofit().create(apiType)
            apis[apiType] = api as Any
            api
        }
    }

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .build()
    }

    protected fun initHttpClient() {
        this.okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }


    protected val isOkHttpClientInitialized get() = this::okHttpClient.isInitialized

}
