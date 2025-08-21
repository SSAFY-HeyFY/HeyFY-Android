package com.ssafy.home.api

import com.ssafy.home.api.response.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {

    @GET("/home")
    suspend fun fetch(): Response<HomeResponse>
}
