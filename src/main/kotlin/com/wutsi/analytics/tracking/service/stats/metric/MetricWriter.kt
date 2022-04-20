package com.wutsi.analytics.tracking.service.stats.metric

import com.wutsi.analytics.tracking.service.stats.AbstractWriter

class MetricWriter : AbstractWriter<Metric>() {
    override fun headers(): Array<String> = arrayOf(
        "time",
        "tenantid",
        "merchantid",
        "productid",
        "count"
    )

    override fun row(data: Metric): Array<String?> = arrayOf(
        data.time.toString(),
        data.tenantId,
        data.merchantId,
        data.productId,
        data.count.toString()
    )
}
