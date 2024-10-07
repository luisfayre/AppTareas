package com.examen.luisalcudia.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope

/***
 * Clase para hacer uso del sharedpreference
 */
class SharePreferenceUtil {

    companion object {

        private const val PREF_USER = "pref_user"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharePreferenceUtil? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharePreferenceUtil =
            instance ?: synchronized(LOCK) {
                instance ?: buildUtil(context).also {
                    instance = it
                }
            }

        private fun buildUtil(context: Context): SharePreferenceUtil {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharePreferenceUtil()
        }
    }

    // Metodo para guardar token del usuario
    fun saveUserToken(token: String) {
        prefs?.let {
            it.edit().putString(PREF_USER, token).apply()
        }
    }

    // Metodo para obtener token del usuario
    fun getUserToken() = prefs?.getString(PREF_USER, "")


}