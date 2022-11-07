package com.example.movieapp.data.local.preference

interface Preferences {
    fun saveIntValue(key: String ,value: Int)
    fun getIntValue(key: String): Int
}