package com.ssafy.home.domain

import com.ssafy.home.domain.model.Home

interface HomeRepository {
    suspend fun fetch(): Result<Home>
}
