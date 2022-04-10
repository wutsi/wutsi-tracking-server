package com.wutsi.analytics.tracking.service.agregator.visit

import com.wutsi.analytics.tracking.service.agregator.AbstractWriter

class VisitWriter : AbstractWriter<Visit>() {
    override fun headers(): Array<String> = arrayOf(
        "time",
        "tenantid",
        "merchantid",
        "productid",
        "count"
    )

    override fun row(data: Visit): Array<String?> = arrayOf(
        data.time.toString(),
        data.tenantId,
        data.merchantId,
        data.productId,
        data.count.toString()
    )
}
