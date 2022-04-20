package com.wutsi.analytics.tracking.service.stats.view

import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.stats.counter.AbstractCounterDailyAggregator
import java.time.LocalDate

/**
 * Aggregate the number visits by product
 */
open class ViewDailyAggregator(date: LocalDate) : AbstractCounterDailyAggregator(date) {
    override fun getEventType() = EventType.VIEW
}
