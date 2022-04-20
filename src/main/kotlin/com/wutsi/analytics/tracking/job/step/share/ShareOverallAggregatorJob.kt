package com.wutsi.analytics.tracking.job.step.share

import com.wutsi.analytics.tracking.job.step.AbstractOverallAggregatorJob
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterOverallAggregator
import com.wutsi.analytics.tracking.service.stats.share.ShareOverallImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShareOverallAggregatorJob(private val overallImporter: ShareOverallImporter) :
    AbstractOverallAggregatorJob<Counter>() {
    override fun getName() = "share"
    override fun getAggregator(date: LocalDate) = CounterOverallAggregator()
    override fun getImporter() = overallImporter

    @Scheduled(cron = "\${wutsi.application.jobs.overall-share.cron}")
    override fun run() {
        super.run()
    }
}