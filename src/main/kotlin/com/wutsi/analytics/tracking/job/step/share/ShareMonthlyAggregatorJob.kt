package com.wutsi.analytics.tracking.job.step.share

import com.wutsi.analytics.tracking.job.step.AbstractMonthlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterMonthlyAggregator
import com.wutsi.analytics.tracking.service.stats.share.ShareMonthlyImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShareMonthlyAggregatorJob(private val monthlyImporter: ShareMonthlyImporter) :
    AbstractMonthlyAggregatorJob<Counter>() {
    override fun getName() = "share"
    override fun getAggregator(date: LocalDate) = CounterMonthlyAggregator()
    override fun getImporter() = monthlyImporter

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-share.cron}")
    override fun run() {
        super.run()
    }
}
