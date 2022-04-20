package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.service.AbstractWriter

class MetricWriter : AbstractWriter<Metric>() {
    override fun headers(): Array<String> = arrayOf(
        "time",
        "tenantid",
        "merchantid",
        "productid",
        "value"
    )

    override fun row(data: Metric): Array<String?> = arrayOf(
        data.time.toString(),
        data.tenantId,
        data.merchantId,
        data.productId,
        data.value.toString()
    )
}
