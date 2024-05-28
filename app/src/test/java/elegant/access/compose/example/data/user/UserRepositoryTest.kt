package elegant.access.compose.example.data.user

import elegant.access.compose.example.infra.user.Avatar
import elegant.access.compose.example.infra.user.Payment
import elegant.access.compose.example.infra.user.SignInResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
@ExperimentalCoroutinesApi
class UserRepositoryTest {
    @Mock
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Mock
    private lateinit var userLocalDataSource: UserLocalDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepository(userRemoteDataSource, userLocalDataSource)
    }

    @Test
    fun `signIn should return success and store response locally`() = runBlockingTest {
        // Arrange
        val email = "test@example.com"
        val pwd = "password"
        val signInResponse = SignInResponse(
            id = "teasaetd",
            nickname = "teasaetd",
            createDate = "teasaetd",
            mailVerify = 1,
            country = "teasaetd",
            avatar = Avatar("asdfads", "test"),
            payment = Payment(1, "22", "33"),
            utoken = "teasaetd",
            )

        val expectedResult = Result.success(signInResponse)

        whenever(userRemoteDataSource.signIn(any(), any())).thenReturn(expectedResult)

        // Act
        val result = userRepository.signIn(email, pwd)

        // Assert
        assert(result.isSuccess)
        verify(userLocalDataSource).storeSignInResponse(signInResponse, email)
    }

    @Test
    fun `signIn should return failure`() = runBlockingTest {
        // Arrange
        val email = "test@example.com"
        val pwd = "password"
        val expectedResult = Result.failure<SignInResponse>(Exception("Sign in failed"))

        whenever(userRemoteDataSource.signIn(any(), any())).thenReturn(expectedResult)

        // Act
        val result = userRepository.signIn(email, pwd)

        // Assert
        assert(result.isFailure)
        verify(userLocalDataSource, never()).storeSignInResponse(any(), any())
    }
}