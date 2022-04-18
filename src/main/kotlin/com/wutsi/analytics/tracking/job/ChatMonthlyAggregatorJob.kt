package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.aggregator.product.Counter
import com.wutsi.analytics.tracking.service.aggregator.product.CounterMonthlyAggregator
import com.wutsi.analytics.tracking.service.importer.product.ChatMonthlyImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChatMonthlyAggregatorJob(private val monthlyImporter: ChatMonthlyImporter) :
    AbstractMonthlyAggregatorJob<Counter>() {
    override fun getName() = "chat"
    override fun getAggregator(date: LocalDate) = CounterMonthlyAggregator()
    override fun getImporter() = monthlyImporter

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-chat.cron}")
    override fun run() {
        super.run()
    }
}
