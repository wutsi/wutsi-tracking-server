package com.wutsi.analytics.tracking.service.stats.chat

import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.stats.metric.AbstractMetricAggregatorDaily
import java.time.LocalDate

/**
 * Aggregate the number chat request by product
 */
open class ChatAggregatorDaily(date: LocalDate) : AbstractMetricAggregatorDaily(date) {
    override fun getEventType() = EventType.CHAT
}
