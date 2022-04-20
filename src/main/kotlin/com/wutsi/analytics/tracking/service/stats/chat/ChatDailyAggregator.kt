package com.wutsi.analytics.tracking.service.stats.chat

import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.stats.counter.AbstractCounterDailyAggregator
import java.time.LocalDate

/**
 * Aggregate the number chat request by product
 */
open class ChatDailyAggregator(date: LocalDate) : AbstractCounterDailyAggregator(date) {
    override fun getEventType() = EventType.CHAT
}
