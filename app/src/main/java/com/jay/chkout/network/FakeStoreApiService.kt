package com.jay.chkout.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
// First the builder is called which is followed by a converter factory which tells Retrofit
// what to do with the data. ScalarsConverterFactory supports strings and thus passed here.
// Then the url which will be the source of the JSON response.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface FakeStoreApiService {
    // This annotation is to tell retrofit that this is the get request and the endpoint of the URL is passed here
    // That means the get request will be on "https://android-kotlin-fun-mars-server.appspot.com/photos"
    @GET("photos")
    suspend fun getPhotos() : String
}

// This is to follow a singleton pattern meaning that there can be only one instance of the specified class,
// object declarations in kotlin are technically classes which have just one object instance at runtime.
// The internals of this object declaration are very similar to "static" in java which means there is
// no need to create an object yourself and directly access properties and functions.
object FakeStoreApi {
    // This is a lazily initialised property which means this wont be instantiated before its first usage
    val fakeStoreApi : FakeStoreApiService by lazy {
        retrofit.create(FakeStoreApiService::class.java)
    }
}