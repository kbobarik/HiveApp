package com.example.hive.domain

sealed class ResultState{

    object Idle : ResultState()
    object Loading: ResultState()
    data class Success(val message:String):ResultState()
    data class Error(val message:String):ResultState()
}