package com.example.hive.data.extensions

fun mapSupabaseError(error: Throwable): SupabaseError {
    val message = error.message?.lowercase() ?: ""

    return when {
        "invalid login credentials" in message ->
            SupabaseError.InvalidCredentials

        "user already registered" in message ->
            SupabaseError.UserAlreadyExists

        "password should be at least" in message ->
            SupabaseError.WeakPassword

        "network" in message || "timeout" in message ->
            SupabaseError.Network

        "unique" in message ->{
            SupabaseError.NotUniqueName
        }
        else ->
            SupabaseError.Unknown
    }
}