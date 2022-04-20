package com.wutsi.analytics.tracking.service.stats.view

import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.stats.metric.AbstractMetricAggregatorDaily
import java.time.LocalDate

/**
 * Aggregate the number visits by product
 */
open class ViewAggregatorDaily(date: LocalDate) : AbstractMetricAggregatorDaily(date) {
    override fun getEventType() = EventType.VIEW
}
