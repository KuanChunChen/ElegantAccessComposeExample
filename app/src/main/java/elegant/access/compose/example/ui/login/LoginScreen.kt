package elegant.access.compose.example.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import elegant.access.compose.example.ElegantAccessScreen
import elegant.access.compose.example.R
import elegant.access.compose.example.ui.component.EmailTextField
import elegant.access.compose.example.ui.component.MainAppBar
import elegant.access.compose.example.ui.component.MainAppBarConfig
import elegant.access.compose.example.ui.component.PasswordTextField
import elegant.access.compose.example.ui.component.isValidEmail
import elegant.access.compose.example.ui.component.isValidPassword
import elegant.access.compose.example.ui.theme.ElegantAccessComposeTheme
import elegant.access.compose.example.util.noRippleClickable

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

@Preview
@Composable
private fun PreviewLoginScreen() {
    ElegantAccessComposeTheme {
        LoginScreen()
    }
}

fun NavGraphBuilder.routeLoginScreen(
    navController: NavController,
) {
    composable(route = ElegantAccessScreen.Login.name) {
        LoginScreen(signInOnClick = { mail, password->
            navController.navigate(ElegantAccessScreen.Chat.name)
        })
    }

}

@Composable
fun LoginScreen(
    lastEmail: String = "",
    signInOnClick: (mail: String, pwd: String) -> Unit = { _, _ -> },
    forgotPasswordOnClick: (String) -> Unit = {},
) {

    Scaffold(
        topBar = {
            LoginAppBar()
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 31.dp)
        ) {
            item {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(id = R.string.welcome_message),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val passwordFocusRequester = remember { FocusRequester() }
                    val email: MutableState<String> = rememberSaveable { mutableStateOf(lastEmail) }
                    EmailTextField(
                        modifier = Modifier.padding(top = 20.dp),
                        inputState = email,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { passwordFocusRequester.requestFocus() }
                        )
                    )
                    val pwd: MutableState<String> = rememberSaveable { mutableStateOf("") }
                    val isValid = isValidEmail(email.value) && isValidPassword(pwd.value)
                    PasswordTextField(
                        modifier = Modifier.padding(top = 20.dp),
                        inputState = pwd,
                        focusRequester = passwordFocusRequester,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                passwordFocusRequester.freeFocus()
                                keyboardController?.hide()
                                if (isValid) {
                                    signInOnClick(email.value, pwd.value)
                                }
                            }
                        )
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.forget_password),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(10.dp)
                                .noRippleClickable {
                                    forgotPasswordOnClick(email.value)
                                }
                        )
                    }
                    Button(
                        onClick = {
                            signInOnClick(email.value, pwd.value)
                            keyboardController?.hide()
                        },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp)
                            .height(42.dp),
                        enabled = isValid,
                        colors = ButtonDefaults.buttonColors().copy(
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_in),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun LoginAppBar() {
    MainAppBar(
        config = MainAppBarConfig(
            titleText = { stringResource(id = R.string.app_name) },
            actionIcon = {
                var expanded by remember { mutableStateOf(false) }
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = ""
                    )
                }
            }
        ),
        elevation = 0.dp,
    )
}