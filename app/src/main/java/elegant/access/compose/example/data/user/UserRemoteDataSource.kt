package elegant.access.compose.example.data.user

import elegant.access.compose.example.infra.user.LoginRestService
import elegant.access.compose.example.infra.user.SignInRequest
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
class UserRemoteDataSource @Inject constructor(
    private val loginRestService: LoginRestService
) {

    suspend fun signIn(email: String, pwd: String): Result<SignInResponse> {
        return loginRestService.signIn(
            SignInRequest(
                mail = email,
                pwd = pwd,
                name = "Your Name",
                lang = "zh-tw",
            )
        )
    }
}