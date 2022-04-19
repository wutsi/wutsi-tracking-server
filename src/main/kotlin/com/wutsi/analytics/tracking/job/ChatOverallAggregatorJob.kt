package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.aggregator.product.Counter
import com.wutsi.analytics.tracking.service.aggregator.product.CounterOverallAggregator
import com.wutsi.analytics.tracking.service.importer.product.ChatOverallImporter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChatOverallAggregatorJob(private val overallImporter: ChatOverallImporter) :
    AbstractOverallAggregatorJob<Counter>() {
    override fun getName() = "chat"
    override fun getAggregator(date: LocalDate) = CounterOverallAggregator()
    override fun getImporter() = overallImporter

    @Scheduled(cron = "\${wutsi.application.jobs.overall-chat.cron}")
    override fun run() {
        super.run()
    }
}
