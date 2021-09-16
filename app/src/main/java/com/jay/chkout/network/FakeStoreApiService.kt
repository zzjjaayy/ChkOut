package com.jay.chkout.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://fakestoreapi.com/"

// Moshi is used to
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FakeStoreApiService {
    // This annotation is to tell retrofit that this is the get request and the endpoint of the URL is passed here
    @GET("products")
    suspend fun getProducts() : List<Product>
}


object FakeStoreApi {
    // This is a lazily initialised property which means this wont be instantiated before its first usage
    val retroFitService : FakeStoreApiService by lazy {
        retrofit.create(FakeStoreApiService::class.java)
    }
}