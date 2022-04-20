package com.wutsi.analytics.tracking.job.step.chat

import com.wutsi.analytics.tracking.job.step.AbstractYearlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.chat.ChatMonthlyImporter
import com.wutsi.analytics.tracking.service.stats.counter.Counter
import com.wutsi.analytics.tracking.service.stats.counter.CounterYearlyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChatYearlyAggregatorJob(private val monthlyImporter: ChatMonthlyImporter) :
    AbstractYearlyAggregatorJob<Counter>() {
    override fun getName() = "chat"
    override fun getAggregator(date: LocalDate) = CounterYearlyAggregator()
    override fun getImporter() = monthlyImporter

    @Scheduled(cron = "\${wutsi.application.jobs.yearly-chat.cron}")
    override fun run() {
        super.run()
    }
}
