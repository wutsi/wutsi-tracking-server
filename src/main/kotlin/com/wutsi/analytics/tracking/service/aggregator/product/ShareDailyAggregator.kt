package com.wutsi.analytics.tracking.service.aggregator.product

import com.wutsi.analytics.tracking.entity.EventType
import java.time.LocalDate

/**
 * Aggregate the number share request by product
 */
open class ShareDailyAggregator(date: LocalDate) : AbstractCounterDailyAggregator(date) {
    override fun getEventType() = EventType.SHARE
}
