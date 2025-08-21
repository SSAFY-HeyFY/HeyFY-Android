package com.ssafy.home.data

import com.ssafy.home.api.response.HomeResponse
import retrofit2.Response

interface HomeDataSource {
    suspend fun fetch(): Response<HomeResponse>
}
