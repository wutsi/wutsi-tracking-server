package com.wutsi.analytics.tracking.service.stats.metric

import com.wutsi.analytics.tracking.service.stats.AbstractCsvMapper

class MetricCsvMapper : AbstractCsvMapper<Metric>() {
    override fun map(col: Array<String>): Metric =
        Metric(
            time = getLong("time", col)!!,
            tenantId = getString("tenantid", col),
            merchantId = getString("merchantid", col),
            productId = getString("productid", col),
            count = getLong("count", col) ?: 0
        )
}
