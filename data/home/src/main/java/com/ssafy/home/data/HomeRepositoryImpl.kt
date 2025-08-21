package com.ssafy.home.data

import com.ssafy.home.api.response.HomeResponse
import com.ssafy.home.domain.HomeRepository
import com.ssafy.home.domain.model.Home
import com.ssafy.network.utils.ApiUtils.safeApiCall
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun fetch(): Result<Home> {
        return safeApiCall(
            apiCall = {
                homeDataSource.fetch()
            },
            convert = { response ->
                response.toHome()
            }
        )
    }

    private fun HomeResponse.toHome(): Home {
        return Home(
            studentId = studentId,
            normalAccount = normalAccount.toAccount(),
            foreignAccount = foreignAccount.toAccount(),
        )
    }

    private fun HomeResponse.Account.toAccount(): Home.Account {
        return Home.Account(
            accountNo = accountNo,
            balance = balance,
            currency = currency,
        )
    }
}
