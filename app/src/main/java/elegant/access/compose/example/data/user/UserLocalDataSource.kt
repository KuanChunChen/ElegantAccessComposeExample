package elegant.access.compose.example.data.user

import elegant.access.compose.example.infra.user.SignInResponse
import elegant.access.compose.example.infra.user.UserDataStore
import javax.inject.Inject

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

class UserLocalDataSource @Inject constructor(
    private val userDataStore: UserDataStore
) {

    fun getUserInfoFlow() = userDataStore.getUserFlow()

    suspend fun storeSignInResponse(userResponse: SignInResponse, email: String) {
        userDataStore.saveUserResponse(userResponse, email)
    }

    suspend fun clear() {
        userDataStore.clear()
    }
}