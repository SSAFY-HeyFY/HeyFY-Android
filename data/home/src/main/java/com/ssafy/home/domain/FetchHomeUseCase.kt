package com.ssafy.home.domain

import com.ssafy.home.domain.model.Home
import javax.inject.Inject

class FetchHomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<Home> {
        return homeRepository.fetch()
    }
}
