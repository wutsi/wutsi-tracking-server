package com.wutsi.analytics.tracking.dto

import kotlin.Boolean
import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Track(
    public val time: Long = 0,
    public val tenantId: String? = null,
    public val correlationId: String? = null,
    public val deviceId: String? = null,
    public val accountId: String? = null,
    public val merchantId: String? = null,
    public val productId: String? = null,
    public val ua: String? = null,
    public val bot: Boolean = false,
    public val ip: String? = null,
    public val lat: Double? = null,
    public val long: Double? = null,
    public val referer: String? = null,
    public val page: String? = null,
    public val event: String? = null,
    public val `value`: Double? = null,
    public val url: String? = null,
    public val deviceType: String? = null,
    public val impressions: String? = null
)
