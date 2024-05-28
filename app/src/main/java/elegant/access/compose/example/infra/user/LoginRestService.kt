package elegant.access.compose.example.infra.user

import retrofit2.http.Body
import retrofit2.http.POST

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
interface LoginRestService {
    @POST("user/signIn")
    suspend fun signIn(@Body signInRequest: SignInRequest): Result<SignInResponse>
}