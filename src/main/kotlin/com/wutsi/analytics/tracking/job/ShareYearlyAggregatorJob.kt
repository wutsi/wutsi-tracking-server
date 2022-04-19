package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.aggregator.product.Counter
import com.wutsi.analytics.tracking.service.aggregator.product.CounterYearlyAggregator
import com.wutsi.analytics.tracking.service.importer.product.ShareYearlyImporter
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
