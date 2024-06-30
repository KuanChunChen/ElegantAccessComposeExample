package elegant.access.compose.example.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import elegant.access.compose.example.R
import elegant.access.compose.example.ui.theme.ElegantAccessComposeTheme
import elegant.access.compose.example.util.noRippleClickable
import java.util.regex.Pattern

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

private const val VALID_MAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && Pattern.compile(VALID_MAIL)
        .matcher(email)
        .find()
}

private const val PASSWORD_MIN_LENGTH = 8
fun isValidPassword(pwd: String): Boolean {
    return pwd.length >= PASSWORD_MIN_LENGTH && !containsSpace(pwd)
}

fun containsSpace(str: String) = str.any { it.isWhitespace() }

@Preview
@Composable
private fun PreviewInputTextField() {
    ElegantAccessComposeTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            EmailTextField()
            PasswordTextField(modifier = Modifier.padding(top = 20.dp))
        }
    }
}


@Preview
@Composable
private fun PreviewFeedbackField() {
    ElegantAccessComposeTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            emailTextHorizontalField()
        }
    }
}

@Composable
fun emailTextHorizontalField(
    initText: String = "",
    modifier: Modifier = Modifier,
): State<String> {
    val inputState = rememberSaveable { mutableStateOf(initText) }

    LaunchedEffect(initText) {
        inputState.value = initText
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.email_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        var focusState by rememberSaveable { mutableStateOf<FocusState?>(null) }

        BasicTextField(
            value = inputState.value,
            onValueChange = {
                inputState.value = it
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .onFocusEvent {
                    focusState = it
                },
            textStyle = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.End),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterEnd) {
                    innerTextField()
                    if (focusState?.hasFocus == false && inputState.value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.email_hint),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.surfaceDim,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            },
        )

    }
    return inputState
}


@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    inputState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.email_title),
            style = MaterialTheme.typography.bodySmall
        )
        var input by rememberSaveable { inputState }
        var focusState by rememberSaveable { mutableStateOf<FocusState?>(null) }
        var isStartChecking by rememberSaveable { mutableStateOf(false) }
        var isError by rememberSaveable { mutableStateOf(false) }
        fun validateEmail() {
            isError = isStartChecking && !isValidEmail(input) && input.isNotEmpty()
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(top = 6.dp)
                .onFocusEvent {
                    focusState = it
                    if (!it.isFocused && input.isNotEmpty()) {
                        isStartChecking = true
                        validateEmail()
                    }
                }
                .border(
                    width = 2.dp,
                    color = if (isError) {
                        MaterialTheme.colorScheme.error
                    } else if (focusState?.isFocused == true) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    shape = MaterialTheme.shapes.small
                ),
            value = input,
            onValueChange = {
                input = it.filter { char -> !char.isWhitespace() }
                validateEmail()
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (focusState?.hasFocus == false && input.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.enter_email),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    } else {
                        innerTextField()
                    }

                    if (input.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            "",
                            tint = MaterialTheme.colorScheme.surfaceDim,
                            modifier = Modifier
                                .clickable {
                                    input = ""
                                    isStartChecking = false
                                    validateEmail()
                                }
                                .size(18.dp)
                        )
                    }

                }
            }
        )
        if (isError) {
            Text(
                text = stringResource(id = R.string.error_email),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    inputState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    focusRequester: FocusRequester = FocusRequester(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.enter_email),
            style = MaterialTheme.typography.bodySmall
        )
        var input by rememberSaveable { inputState }
        var focusState by rememberSaveable { mutableStateOf<FocusState?>(null) }
        var isVisible by rememberSaveable { mutableStateOf(false) }
        var isError by rememberSaveable {
            mutableStateOf(false)
        }

        fun validatePwd() {
            isError = input.length >= PASSWORD_MIN_LENGTH && containsSpace(input)
        }
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(top = 6.dp)
                .onFocusEvent {
                    focusState = it
                }
                .border(
                    width = 2.dp,
                    color = if (isError) {
                        MaterialTheme.colorScheme.error
                    } else if (focusState?.isFocused == true) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    shape = MaterialTheme.shapes.small
                )
                .focusRequester(focusRequester),
            value = input,
            onValueChange = {
                input = it
                validatePwd()
            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (focusState?.hasFocus == false && input.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.enter_password),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    } else {
                        innerTextField()
                    }

                    if (input.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "",
                            tint = if (isVisible && focusState?.hasFocus == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceDim,
                            modifier = Modifier
                                .noRippleClickable {
                                    isVisible = !isVisible
                                }
                                .size(20.dp)
                        )
                    }
                }
            }
        )
        if (isError) {
            Text(
                text = stringResource(id = R.string.password_strong_error_symbol),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}