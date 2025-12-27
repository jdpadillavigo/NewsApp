package com.example.newapp.core.data.networking

import com.example.newapp.core.domain.util.NetworkError
import com.example.newapp.core.presentation.util.toMessage
import com.example.newapp.news.data.networking.dto.NewResponseDto
import com.example.newapp.news.domain.NewResponse
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend fun safeCall(
    execute: suspend () -> HttpResponse,
    onSuccess: (NewResponseDto) -> NewResponse
): NewResponse {
    return try {
        val response = execute()
        when (response.status.value) {
            in 200..299 -> {
                try {
                    val responseBody = response.body<NewResponseDto>()
                    onSuccess(responseBody)
                } catch (e: NoTransformationFoundException) {
                    println(NetworkError.SERIALIZATION.toMessage())
                    NewResponse(status = "error")
                }
            }
            408 -> {
                println(NetworkError.REQUEST_TIMEOUT.toMessage())
                NewResponse(status = "error")
            }
            429 -> {
                println(NetworkError.TOO_MANY_REQUESTS.toMessage())
                NewResponse(status = "error")
            }
            in 500..599 -> {
                println(NetworkError.SERVER_ERROR.toMessage())
                NewResponse(status = "error")
            }
            else -> {
                println(NetworkError.UNKNOWN.toMessage())
                NewResponse(status = "error")
            }
        }
    } catch (e: UnresolvedAddressException) {
        println(NetworkError.NO_INTERNET.toMessage())
        NewResponse(status = "error")
    } catch (e: SerializationException) {
        println(NetworkError.SERIALIZATION.toMessage())
        NewResponse(status = "error")
    } catch (e: Exception) {
        println(NetworkError.UNKNOWN.toMessage())
        NewResponse(status = "error")
    }
}