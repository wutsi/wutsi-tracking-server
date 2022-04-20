package com.wutsi.analytics.tracking.job.step.chat

import com.wutsi.analytics.tracking.job.step.AbstractOverallAggregatorJob
import com.wutsi.analytics.tracking.service.stats.chat.ChatOverallImporter
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterOverallAggregator
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
