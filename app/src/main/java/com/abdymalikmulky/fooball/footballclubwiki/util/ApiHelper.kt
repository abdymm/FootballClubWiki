package com.abdymalikmulky.fooball.footballclubwiki.util

import com.abdymalikmulky.fooball.footballclubwiki.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiHelper {
    val BASE_URL = BuildConfig.BASE_URL
    private var retrofit: Retrofit? = null

    fun client(): Retrofit? {
        val gson = GsonBuilder()
                .setLenient()
                .create()


        //add cache to the client
        val client = OkHttpClient.Builder()
                .addInterceptor(InterceptorUtil.getLoggingInterceptor())
                .build()


        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

        return retrofit
    }


}
