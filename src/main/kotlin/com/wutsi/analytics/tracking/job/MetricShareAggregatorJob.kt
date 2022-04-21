package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.entity.MetricType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MetricShareAggregatorJob : AbstractMetricAggregatorJob() {
    override fun getMetricType() = MetricType.SHARE
    override fun getJobName() = "metric-share"

    @Scheduled(cron = "\${wutsi.application.jobs.metric-share.cron}")
    override fun run() {
        super.run()
    }
}
