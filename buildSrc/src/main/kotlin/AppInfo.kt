import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppInfo(
    @SerialName("package_name")
    val packageName: String,
    @SerialName("version_code")
    val versionCode: Int,
    @SerialName("version_name")
    val versionName: String,
    @SerialName("device_type")
    val deviceType: Int,
    @SerialName("app_config")
    val appConfig: String
)