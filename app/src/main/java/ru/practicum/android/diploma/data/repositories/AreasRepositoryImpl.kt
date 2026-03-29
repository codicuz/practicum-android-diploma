package ru.practicum.android.diploma.data.repositories

import ru.practicum.android.diploma.data.dto.Areas
import ru.practicum.android.diploma.data.dto.AreasRequest
import ru.practicum.android.diploma.data.dto.AreasResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.models.AreasRepository
import ru.practicum.android.diploma.util.Constants.HTTP_OK

class AreasRepositoryImpl(private val networkClient: NetworkClient) : AreasRepository {
    override suspend fun getAreas(): Result<List<Areas>> {
        val response = networkClient.doRequest(AreasRequest())

        return when (response.resultCode) {
            HTTP_OK -> {
                val areasResponse = response as? AreasResponse
                if (areasResponse != null) {
                    Result.success(areasResponse.results)
                } else {
                    Result.failure(Exception("Error data format"))
                }
            }

            else -> Result.failure(Exception("Server error: ${response.resultCode}"))
        }
    }
}
