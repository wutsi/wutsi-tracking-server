package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.entity.EventType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MetricViewAggregatorJob : AbstractMetricAggregatorJob() {
    override fun getEventType() = EventType.VIEW
    override fun getJobName() = "metric-view"

    @Scheduled(cron = "\${wutsi.application.jobs.metric-view.cron}")
    override fun run() {
        super.run()
    }
}
