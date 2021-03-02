package com.mashkov.mvvm.network.apis

import com.mashkov.mvvm.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class ApiProvider {

    protected lateinit var okHttpClient: OkHttpClient

    protected val apis: MutableMap<Class<*>, Any> = HashMap()

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//    private val interceptors: MutableSet<DiiaInterceptor> = mutableSetOf(
//        AppInfoHeaderInterceptor(),
//        AnalyticsInterceptor(),
//        RetryInterceptor(),
//        DiiaHttpLoggingInterceptor(),
//    )

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
        val builder = OkHttpClient.Builder()/*.apply {
            setTimeout()
            preMarshmallowSetUp()
            setCert()
            debugSetUp()
        }
        interceptors.sortedBy { it.getWeight() }.forEach {
            builder.addInterceptor(it)
        }*/
        this.okHttpClient = builder.build()
    }

//    protected fun addInterceptor(interceptor: DiiaInterceptor) {
//        interceptors.add(interceptor)
//    }

    protected val isOkHttpClientInitialized get() = this::okHttpClient.isInitialized

}
