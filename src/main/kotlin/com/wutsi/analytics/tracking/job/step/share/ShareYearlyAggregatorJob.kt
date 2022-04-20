package com.wutsi.analytics.tracking.job.step.share

import com.wutsi.analytics.tracking.job.step.AbstractYearlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterYearlyAggregator
import com.wutsi.analytics.tracking.service.stats.share.ShareYearlyImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShareYearlyAggregatorJob(private val yearlyImporter: ShareYearlyImporter) :
    AbstractYearlyAggregatorJob<Counter>() {
    override fun getName() = "share"
    override fun getAggregator(date: LocalDate) = CounterYearlyAggregator()
    override fun getImporter() = yearlyImporter

    @Scheduled(cron = "\${wutsi.application.jobs.yearly-share.cron}")
    override fun run() {
        super.run()
    }
}
