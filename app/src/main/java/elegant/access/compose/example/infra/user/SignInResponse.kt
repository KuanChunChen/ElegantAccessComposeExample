package elegant.access.compose.example.infra.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

@Serializable
data class Avatar(
    @SerialName("url") val url: String?,
    @SerialName("last_updated") val lastUpdated: String?
)

@Serializable
data class Payment(
    @SerialName("vip_type") val vipType: Int,
    @SerialName("vip_start_time") val vipStartTime: String?,
    @SerialName("vip_end_time") val vipEndTime: String?
)

@Serializable
data class SignInResponse(
    @SerialName("id") val id: String,
    @SerialName("nickname") val nickname: String? = null,
    @SerialName("create_date") val createDate: String? = null,
    @SerialName("mail_verify") val mailVerify: Int? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("avatar") val avatar: Avatar? = null,
    @SerialName("payment") val payment: Payment? = null,
    @SerialName("utoken") val utoken: String,
)