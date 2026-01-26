package com.example.hive.data.extensions


fun <T> Result<T>.mapError(): Result<T> =
    fold(
        onSuccess = { Result.success(it) },
        onFailure = { error ->
            Result.failure(Throwable(mapSupabaseError(error).message))
        }
    )