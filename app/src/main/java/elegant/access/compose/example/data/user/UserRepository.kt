package elegant.access.compose.example.data.user

import elegant.access.compose.example.infra.user.SignInResponse
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
class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) {

    suspend fun signIn(email: String, pwd: String): Result<SignInResponse> {
        val result = userRemoteDataSource.signIn(email, pwd)
        return result.onSuccess {
            userLocalDataSource.storeSignInResponse(it, email)
        }
    }
}