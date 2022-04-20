package com.wutsi.analytics.tracking.service.stats.metric

data class Metric(
    var time: Long = 0,
    var tenantId: String? = null,
    var merchantId: String? = null,
    var productId: String? = null,
    var count: Long = 1
)
