package com.wutsi.analytics.tracking.job.step.share

import com.wutsi.analytics.tracking.job.step.AbstractYearlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.metric.Metric
import com.wutsi.analytics.tracking.service.stats.metric.MetricYearlyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShareYearlyAggregatorJob : AbstractYearlyAggregatorJob<Metric>() {
    override fun getName() = "share"
    override fun getAggregator(date: LocalDate) = MetricYearlyAggregator()

    @Scheduled(cron = "\${wutsi.application.jobs.yearly-share.cron}")
    override fun run() {
        super.run()
    }
}
