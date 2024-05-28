package elegant.access.compose.example.ui

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

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
@HiltAndroidApp
class ElegantAccessApplication : Application(), CoroutineScope {

    companion object {
        private const val TAG = "ElegantAccessApplication"
    }
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

}