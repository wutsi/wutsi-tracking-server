package com.wutsi.analytics.tracking.job.step.chat

import com.wutsi.analytics.tracking.job.step.AbstractMonthlyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.metric.Metric
import com.wutsi.analytics.tracking.service.stats.metric.MetricMonthlyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChatMonthlyAggregatorJob : AbstractMonthlyAggregatorJob<Metric>() {
    override fun getName() = "chat"
    override fun getAggregator(date: LocalDate) = MetricMonthlyAggregator()

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-chat.cron}")
    override fun run() {
        super.run()
    }
}
