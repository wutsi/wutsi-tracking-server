package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.aggregator.product.Counter
import com.wutsi.analytics.tracking.service.aggregator.product.CounterMonthlyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChatMonthlyAggregatorJob : AbstractMonthlyAggregatorJob<Counter>() {
    override fun getAggregator(date: LocalDate) = CounterMonthlyAggregator()
    override fun getName() = "chat"

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-chat.cron}")
    override fun run() {
        super.run()
    }
}
