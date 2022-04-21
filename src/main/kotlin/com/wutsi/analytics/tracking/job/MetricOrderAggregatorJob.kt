package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.entity.MetricType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MetricOrderAggregatorJob : AbstractMetricAggregatorJob() {
    override fun getMetricType() = MetricType.ORDER
    override fun getJobName() = "metric-order"

    @Scheduled(cron = "\${wutsi.application.jobs.metric-order.cron}")
    override fun run() {
        super.run()
    }
}
