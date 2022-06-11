package com.android.screen_capture.datasource

import com.android.screen_capture.utils.Results
import retrofit2.Response

suspend fun <T> getResult(call: suspend () -> Response<T>): Results<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Results.Success(body)
        }
        return error(" ${response.code()} ${response.message()} ")
    } catch (e: Exception) {
        return error(e.message ?: e.toString())
    }
}

private fun <T> error(message: String): Results<T> {
    //Timber.e(message)
    // return Result.Erro("Network call has failed for a following reason: $message")
    return Results.Error(message)
}