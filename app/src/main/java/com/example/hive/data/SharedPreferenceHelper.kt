package com.example.hive.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferenceHelper(private val sharedPreferences: SharedPreferences) {

    companion object{
        private const val MY_PREF_KEY = "MY_PREF"
    }

    fun saveStringData(key:String,data:String?)
    {
        sharedPreferences.edit { putString(key, data) }
    }
    fun getStringData(key:String):String?
    {
        return sharedPreferences.getString(key,null)
    }
    fun clearPreferences()
    {
        sharedPreferences.edit { clear() }
    }
}