package com.ssafy.home.data

import com.ssafy.home.api.HomeApi
import com.ssafy.home.api.response.HomeResponse
import retrofit2.Response
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeApi: HomeApi
): HomeDataSource {
    override suspend fun fetch(): Response<HomeResponse> {
        return homeApi.fetch()
    }
}
