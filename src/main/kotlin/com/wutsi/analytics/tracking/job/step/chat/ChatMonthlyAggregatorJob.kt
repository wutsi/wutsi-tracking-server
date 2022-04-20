package com.wutsi.analytics.tracking.job.step.chat

import com.wutsi.analytics.tracking.job.step.AbstractMonthlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.chat.ChatMonthlyImporter
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterMonthlyAggregator
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
