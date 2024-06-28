package elegant.access.compose.example.data.system

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import elegant.access.compose.example.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This file is part of an Android project developed by elegant.access.
 *
 * For more information about this project, you can visit our website:
 * {@link https://elegantaccess.org}
 *
 * Please note that this project is for educational purposes only and is not intended
 * for use in production environments.
 *
 * @author Willy.Chen
 * @version 1.0.0
 * @since 2020~2024
 */
@Singleton
class SettingDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val IS_ENABLED_DEBUG_MODE = booleanPreferencesKey("is_enabled_debug_mode")
        private val IS_USING_DEBUG_CONFIG = booleanPreferencesKey("is_using_debug_config")
    }

    private val Context.settingPreferencesDataStore: DataStore<Preferences> by preferencesDataStore("settings")

    fun isEnabledDebugMode(): Flow<Boolean> {
        return context.settingPreferencesDataStore.data.map { preferences ->
            preferences[IS_ENABLED_DEBUG_MODE] ?: false
        }
    }

    suspend fun toggleDebugMode() {
        context.settingPreferencesDataStore.edit { settings ->
            settings[IS_ENABLED_DEBUG_MODE] = !(settings[IS_ENABLED_DEBUG_MODE] ?: false)
        }
    }

    fun appConfigFlow(): Flow<AppConfig> {
        return context.settingPreferencesDataStore.data.map { preferences ->
            val useDebugConfig = preferences[IS_USING_DEBUG_CONFIG] ?: BuildConfig.IS_USING_DEBUG_CONFIG
            if (useDebugConfig) {
                AppConfig.Test
            } else {
                AppConfig.Release
            }
        }
    }
}