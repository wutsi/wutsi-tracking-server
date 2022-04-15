package com.wutsi.analytics.tracking.service.aggregator.product

import com.wutsi.analytics.tracking.entity.EventType
import java.time.LocalDate

/**
 * Aggregate the number visits by product
 */
open class VisitDailyAggregator(date: LocalDate) : AbstractCounterDailyAggregator(date) {
    override fun getEventType() = EventType.VIEW
}
