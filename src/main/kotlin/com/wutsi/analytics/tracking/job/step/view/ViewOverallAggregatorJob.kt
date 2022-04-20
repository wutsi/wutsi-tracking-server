package com.wutsi.analytics.tracking.job.step.view

import com.wutsi.analytics.tracking.job.step.AbstractOverallAggregatorJob
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterOverallAggregator
import com.wutsi.analytics.tracking.service.stats.view.ViewOverallImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ViewOverallAggregatorJob(private val overallImporter: ViewOverallImporter) :
    AbstractOverallAggregatorJob<Counter>() {
    override fun getName() = "view"
    override fun getAggregator(date: LocalDate) = CounterOverallAggregator()
    override fun getImporter() = overallImporter

    @Scheduled(cron = "\${wutsi.application.jobs.overall-view.cron}")
    override fun run() {
        super.run()
    }
}
