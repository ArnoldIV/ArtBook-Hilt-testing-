package com.arnold.hilttestingapplicationartbook.api

import com.arnold.hilttestingapplicationartbook.model.ImageResponse
import com.arnold.hilttestingapplicationartbook.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") SearchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ) : Response<ImageResponse>
}