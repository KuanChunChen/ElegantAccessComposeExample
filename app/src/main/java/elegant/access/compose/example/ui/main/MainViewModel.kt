package elegant.access.compose.example.ui.main

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var navController: NavController? = null
}