package com.wutsi.analytics.tracking.job.step.view

import com.wutsi.analytics.tracking.job.step.AbstractOverallAggregatorJob
import com.wutsi.analytics.tracking.service.stats.metric.Metric
import com.wutsi.analytics.tracking.service.stats.metric.MetricOverallAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ViewOverallAggregatorJob : AbstractOverallAggregatorJob<Metric>() {
    override fun getName() = "view"
    override fun getAggregator(date: LocalDate) = MetricOverallAggregator()

    @Scheduled(cron = "\${wutsi.application.jobs.overall-view.cron}")
    override fun run() {
        super.run()
    }
}
