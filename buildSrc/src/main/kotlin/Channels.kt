import kotlinx.serialization.Serializable

@Serializable
data class Channels(
    val channels: Map<String, AppInfo>,
)

