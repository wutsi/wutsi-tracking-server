package com.wutsi.analytics.tracking.service.agregator.visit

data class Visit(
    val time: Long = 0,
    val tenantId: String? = null,
    val accountId: String? = null,
    val merchantId: String? = null,
    val productId: String? = null,
    var count: Long = 1
)
