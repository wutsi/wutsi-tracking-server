package com.wutsi.analytics.tracking.job.step.share

import com.wutsi.analytics.tracking.job.step.AbstractOverallAggregatorJob
import com.wutsi.analytics.tracking.service.stats.metric.Metric
import com.wutsi.analytics.tracking.service.stats.metric.MetricOverallAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShareOverallAggregatorJob : AbstractOverallAggregatorJob<Metric>() {
    override fun getName() = "share"
    override fun getAggregator(date: LocalDate) = MetricOverallAggregator()

    @Scheduled(cron = "\${wutsi.application.jobs.overall-share.cron}")
    override fun run() {
        super.run()
    }
}
