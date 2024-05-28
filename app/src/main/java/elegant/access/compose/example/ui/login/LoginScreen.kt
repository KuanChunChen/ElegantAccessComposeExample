package elegant.access.compose.example.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import elegant.access.compose.example.R
import elegant.access.compose.example.ui.component.MainAppBar
import elegant.access.compose.example.ui.component.MainAppBarConfig
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
private fun PreviewLoginScreen() {
    ElegantAccessComposeTheme {
        LoginScreen { _, _ -> }
    }
}

@Composable
fun LoginScreen(signInOnClick: (mail: String, pwd: String) -> Unit,) {
    Scaffold(
        topBar = {
            LoginAppBar()
        }
    ){
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 31.dp)
                .verticalScroll(rememberScrollState())
        ) {

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