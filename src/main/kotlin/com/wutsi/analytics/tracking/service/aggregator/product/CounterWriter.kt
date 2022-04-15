package com.wutsi.analytics.tracking.service.aggregator.product

import com.wutsi.analytics.tracking.service.aggregator.AbstractWriter

class CounterWriter : AbstractWriter<Counter>() {
    override fun headers(): Array<String> = arrayOf(
        "time",
        "tenantid",
        "merchantid",
        "productid",
        "count"
    )

    override fun row(data: Counter): Array<String?> = arrayOf(
        data.time.toString(),
        data.tenantId,
        data.merchantId,
        data.productId,
        data.count.toString()
    )
}
