package elegant.access.compose.example.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import elegant.access.compose.example.R
import elegant.access.compose.example.ui.theme.ElegantAccessComposeTheme

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