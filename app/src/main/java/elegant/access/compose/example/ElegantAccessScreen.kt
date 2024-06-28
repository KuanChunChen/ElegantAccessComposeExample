package elegant.access.compose.example

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import elegant.access.compose.example.ui.chatroom.routeChatScreen
import elegant.access.compose.example.ui.login.routeLoginScreen
import elegant.access.compose.example.ui.main.MainViewModel

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

enum class ElegantAccessScreen {
    Login,
    Main,
    Feedback,
    About,
    Chat,
}

@Composable
fun ElegantAccessApp(
    vm: MainViewModel,
    isLoginState: Boolean,
    navController: NavHostController = rememberNavController(),
) {
    vm.navController = navController
    NavHost(
        navController = navController,
        startDestination = ElegantAccessScreen.Login.name,
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        routeLoginScreen(navController)
        routeChatScreen(navController)
    }
}
