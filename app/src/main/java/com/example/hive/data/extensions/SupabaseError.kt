package com.example.hive.data.extensions

sealed class SupabaseError(val message:String = "") {
    object InvalidCredentials : SupabaseError("Неверные данные для входа")
    object NotUniqueName : SupabaseError("Никнейм занят")
    object UserAlreadyExists : SupabaseError("Пользователь уже зарегистрирован")
    object WeakPassword : SupabaseError("Пароль должен быть не менее 6 символов")
    object Network : SupabaseError("Ошибка в работе сети")
    object Unknown : SupabaseError("Ошибка")
}