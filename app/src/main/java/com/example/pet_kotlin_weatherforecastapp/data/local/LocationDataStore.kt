package com.example.pet_kotlin_weatherforecastapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "location_prefs")

object LocationPrefs {
    val LATITUDE = doublePreferencesKey("latitude")
    val LONGITUDE = doublePreferencesKey("longitude")

    suspend fun saveLocation(context: Context, lat: Double, lon: Double) {
        context.dataStore.edit {
            it[LATITUDE] = lat
            it[LONGITUDE] = lon
        }
    }

    suspend fun getSavedLocation(context: Context): Pair<Double, Double>? {
        val prefs = context.dataStore.data.first()
        val lat = prefs[LATITUDE]
        val lon = prefs[LONGITUDE]
        return if (lat != null && lon != null) Pair(lat, lon) else null
    }
}
