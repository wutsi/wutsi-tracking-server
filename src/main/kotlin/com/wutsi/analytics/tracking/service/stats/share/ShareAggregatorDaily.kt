package com.wutsi.analytics.tracking.service.stats.share

import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.stats.metric.AbstractMetricAggregatorDaily
import java.time.LocalDate

/**
 * Aggregate the number share request by product
 */
open class ShareAggregatorDaily(date: LocalDate) : AbstractMetricAggregatorDaily(date) {
    override fun getEventType() = EventType.SHARE
}
