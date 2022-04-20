package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.service.AbstractCsvMapper

class MetricCsvMapper : AbstractCsvMapper<Metric>() {
    override fun map(col: Array<String>): Metric =
        Metric(
            time = getLong("time", col)!!,
            tenantId = getString("tenantid", col),
            merchantId = getString("merchantid", col),
            productId = getString("productid", col),
            value = getLong("value", col) ?: 0
        )
}
