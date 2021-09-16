package com.jay.chkout.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://fakestoreapi.com/"
// First the builder is called which is followed by a converter factory which tells Retrofit
// what to do with the data. ScalarsConverterFactory supports strings and thus passed here.
// Then the url which will be the source of the JSON response.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface FakeStoreApiService {
    // This annotation is to tell retrofit that this is the get request and the endpoint of the URL is passed here
    @GET("products")
    suspend fun getProducts() : String
}


object FakeStoreApi {
    // This is a lazily initialised property which means this wont be instantiated before its first usage
    val retroFitService : FakeStoreApiService by lazy {
        retrofit.create(FakeStoreApiService::class.java)
    }
}