package ru.practicum.android.diploma.data.datasources

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.presentation.filter.FilterState

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "filters")

class FilterDataStore(private val context: Context) {

    companion object {
        private val SALARY_KEY = stringPreferencesKey("salary")
        private val WITHOUT_SALARY_KEY = booleanPreferencesKey("without_salary")
        private val COUNTRY_ID_KEY = intPreferencesKey("country_id")
        private val COUNTRY_NAME_KEY = stringPreferencesKey("country_name")
        private val REGION_ID_KEY = intPreferencesKey("region_id")
        private val REGION_NAME_KEY = stringPreferencesKey("region_name")
        private val INDUSTRY_ID_KEY = intPreferencesKey("industry_id")
        private val INDUSTRY_NAME_KEY = stringPreferencesKey("industry_name")
    }

    val filterStateFlow: Flow<FilterState> = context.dataStore.data.map { preferences ->
        FilterState(
            salary = preferences[SALARY_KEY] ?: "",
            isWithoutSalayrHidden = preferences[WITHOUT_SALARY_KEY] ?: false,
            selectedCountryId = preferences[COUNTRY_ID_KEY],
            selectedCountryName = preferences[COUNTRY_NAME_KEY],
            selectedRegionId = preferences[REGION_ID_KEY],
            selectedRegionName = preferences[REGION_NAME_KEY],
            selectedIndustryId = preferences[INDUSTRY_ID_KEY],
            selectedIndustryName = preferences[INDUSTRY_NAME_KEY]
        )
    }

    suspend fun saveFilterState(state: FilterState) {
        context.dataStore.edit { preferences ->
            with(preferences) {
                putString(SALARY_KEY, state.salary)
                putBoolean(WITHOUT_SALARY_KEY, state.isWithoutSalayrHidden)

                putOrRemove(COUNTRY_ID_KEY, state.selectedCountryId)
                putOrRemove(COUNTRY_NAME_KEY, state.selectedCountryName)
                putOrRemove(REGION_ID_KEY, state.selectedRegionId)
                putOrRemove(REGION_NAME_KEY, state.selectedRegionName)
                putOrRemove(INDUSTRY_ID_KEY, state.selectedIndustryId)
                putOrRemove(INDUSTRY_NAME_KEY, state.selectedIndustryName)
            }
        }
    }

    private fun <T> MutablePreferences.putOrRemove(key: Preferences.Key<T>, value: T?) {
        if (value != null) {
            this[key] = value
        } else {
            remove(key)
        }
    }

    suspend fun clearFilters() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

private fun MutablePreferences.putString(key: Preferences.Key<String>, value: String) {
    this[key] = value
}

private fun MutablePreferences.putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
    this[key] = value
}
