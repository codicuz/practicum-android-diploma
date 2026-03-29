package ru.practicum.android.diploma.data.repositories

import ru.practicum.android.diploma.data.dto.Industries
import ru.practicum.android.diploma.data.dto.IndustriesRequest
import ru.practicum.android.diploma.data.dto.IndustriesResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.IndustriesRepository

class IndustriesRepositoryImpl(private val networkClient: NetworkClient) : IndustriesRepository {
    override suspend fun getIndustries(): Result<List<Industries>> {
        val response = networkClient.doRequest(IndustriesRequest())

        return when (response.resultCode) {
            ReturnCode200 -> {
                val industriesResponse = response as? IndustriesResponse
                if (industriesResponse != null) {
                    Result.success(industriesResponse.results)
                } else {
                    Result.failure(Exception("Error data format"))
                }
            }

            else -> Result.failure(Exception("Server error: ${response.resultCode}"))
        }
    }
}
