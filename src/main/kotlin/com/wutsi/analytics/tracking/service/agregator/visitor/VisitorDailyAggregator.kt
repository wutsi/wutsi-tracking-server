package com.wutsi.analytics.tracking.service.agregator.visitor

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.service.InputStreamIterator
import com.wutsi.analytics.tracking.service.agregator.visit.Visit
import com.wutsi.analytics.tracking.service.agregator.visit.VisitDailyAggregator
import java.time.LocalDate

/**
 * Aggregate the number visitors by product
 */
class VisitorDailyAggregator(date: LocalDate) : VisitDailyAggregator(date) {
    override fun loadItems(iterator: InputStreamIterator): List<Visit> {
        val items = super.loadItems(iterator)
        return reduce(items)
    }

    override fun getKey(track: Track): String =
        "${track.productId}-${track.accountId}"

    private fun reduce(items: List<Visit>): List<Visit> {
        val groups = items.groupBy { it.productId }
        return groups.keys.map {
            val visit = groups[it]!![0]
            Visit(
                time = visit.time,
                tenantId = visit.tenantId,
                merchantId = visit.merchantId,
                accountId = visit.accountId,
                productId = visit.productId,
                count = groups[it]!!.size.toLong()
            )
        }
    }
}
