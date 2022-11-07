package com.example.movieapp.data.local.preference


import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesImp @Inject constructor(@ApplicationContext private val context: Context):
    Preferences {

    private val sharedPref: SharedPreferences by lazy {
        context.getSharedPreferences(
            "movie_pref",
            Context.MODE_PRIVATE
        )
    }

    override fun saveIntValue(key: String, value: Int) {
        with(sharedPref.edit()) {
            putInt(key, value)
            apply()
        }
    }

    override fun getIntValue(key: String): Int {
        return sharedPref.getInt(key, 0)
    }
}