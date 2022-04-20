package com.wutsi.analytics.tracking.job.step.view

import com.wutsi.analytics.tracking.job.step.AbstractYearlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterYearlyAggregator
import com.wutsi.analytics.tracking.service.stats.view.ViewYearlyImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ViewYearlyAggregatorJob(private val yearlyImporter: ViewYearlyImporter) : AbstractYearlyAggregatorJob<Counter>() {
    override fun getName() = "view"
    override fun getAggregator(date: LocalDate) = CounterYearlyAggregator()
    override fun getImporter() = yearlyImporter

    @Scheduled(cron = "\${wutsi.application.jobs.yearly-view.cron}")
    override fun run() {
        super.run()
    }
}
