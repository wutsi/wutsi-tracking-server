package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.aggregator.product.Counter
import com.wutsi.analytics.tracking.service.aggregator.product.CounterMonthlyAggregator
import com.wutsi.analytics.tracking.service.importer.product.ViewMonthlyImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ViewMonthlyAggregatorJob(private val monthlyImporter: ViewMonthlyImporter) :
    AbstractMonthlyAggregatorJob<Counter>() {
    override fun getName() = "view"
    override fun getAggregator(date: LocalDate) = CounterMonthlyAggregator()
    override fun getImporter() = monthlyImporter

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-view.cron}")
    override fun run() {
        super.run()
    }
}
