package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.entity.EventType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MetricChatAggregatorJob : AbstractMetricAggregatorJob() {
    override fun getEventType() = EventType.CHAT
    override fun getJobName() = "metric-chat"

    @Scheduled(cron = "\${wutsi.application.jobs.metric-chat.cron}")
    override fun run() {
        super.run()
    }
}
