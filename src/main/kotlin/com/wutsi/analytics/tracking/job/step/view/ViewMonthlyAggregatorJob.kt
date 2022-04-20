package com.wutsi.analytics.tracking.job.step.view

import com.wutsi.analytics.tracking.job.step.AbstractMonthlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.metric.Metric
import com.wutsi.analytics.tracking.service.stats.metric.MetricMonthlyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ViewMonthlyAggregatorJob : AbstractMonthlyAggregatorJob<Metric>() {
    override fun getName() = "view"
    override fun getAggregator(date: LocalDate) = MetricMonthlyAggregator()

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-view.cron}")
    override fun run() {
        super.run()
    }
}
