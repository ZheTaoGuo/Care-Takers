package com.example.caresync.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.caresync.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define DataStore instance
private val Context.dataStore by preferencesDataStore(name = "login_prefs")

object LoginStateDataStore {
    private val LOGIN_STATE_KEY = stringPreferencesKey("login_state")

    // Save login state to DataStore
    suspend fun saveLoginState(context: Context, state: LoginState) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_STATE_KEY] = state.name // Save as string
        }
    }

    // Read login state from DataStore
    fun getLoginState(context: Context): Flow<LoginState> {
        return context.dataStore.data.map { preferences ->
            when (preferences[LOGIN_STATE_KEY]) {
                "PATIENT" -> LoginState.PATIENT
                "CAREGIVER" -> LoginState.CAREGIVER
                else -> LoginState.UNKNOWN
            }
        }
    }
}
