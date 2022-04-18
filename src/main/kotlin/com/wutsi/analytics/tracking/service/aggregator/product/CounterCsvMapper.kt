package com.wutsi.analytics.tracking.service.aggregator.product

import com.wutsi.analytics.tracking.service.aggregator.AbstractCsvMapper

class CounterCsvMapper : AbstractCsvMapper<Counter>() {
    override fun map(col: Array<String>): Counter =
        Counter(
            time = getLong("time", col)!!,
            tenantId = getString("tenantid", col),
            merchantId = getString("merchantid", col),
            productId = getString("productid", col),
            count = getLong("count", col) ?: 0
        )
}
