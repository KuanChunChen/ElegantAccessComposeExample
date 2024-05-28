package elegant.access.compose.example.infra.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import elegant.access.compose.example.data.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
class UserDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
    companion object {
        private val USER_INFO = stringPreferencesKey("user_info")
        private val USER_EMAIL = stringPreferencesKey("email")
    }

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore("user")

    fun getUserFlow(): Flow<UserInfo?> {
        return context.userPreferencesDataStore.data.map {
            val mail = it[USER_EMAIL] ?: ""
            val signInResponse =
                it[USER_INFO]?.let { json.decodeFromString<SignInResponse>(it) } ?: return@map null
            UserInfo(
                avatarUrl = signInResponse.avatar?.url ?: "",
                name = signInResponse.nickname ?: "",
                email = mail
            )
        }.flowOn(Dispatchers.Default)
    }

    suspend fun getSignInResponse(): SignInResponse? {
        val userInfoStr = context.userPreferencesDataStore.data.first()[USER_INFO] ?: return null
        return try {
            json.decodeFromString<SignInResponse>(userInfoStr)
        } catch (ignore: Throwable) {
            return null
        }
    }

    suspend fun saveUserResponse(signInResponse: SignInResponse, email: String) {
        context.userPreferencesDataStore.edit { userPreferences ->
            userPreferences[USER_INFO] = json.encodeToString(signInResponse)
            userPreferences[USER_EMAIL] = email
        }
    }

    suspend fun clear() {
        context.userPreferencesDataStore.edit {
            it.clear()
        }
    }
}