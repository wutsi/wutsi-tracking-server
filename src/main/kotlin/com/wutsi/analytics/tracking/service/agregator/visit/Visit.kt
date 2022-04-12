package com.wutsi.analytics.tracking.service.agregator.visit

data class Visit(
    var time: Long = 0,
    var tenantId: String? = null,
    var accountId: String? = null,
    var merchantId: String? = null,
    var productId: String? = null,
    var count: Long = 1
)
