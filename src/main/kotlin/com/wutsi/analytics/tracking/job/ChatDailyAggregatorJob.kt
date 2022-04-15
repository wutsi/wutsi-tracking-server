package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.aggregator.product.ChatDailyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ChatDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getAggregator(date: LocalDate) = ChatDailyAggregator(date)
    override fun getName() = "chat"

    @Scheduled(cron = "\${wutsi.application.jobs.daily-chat.cron}")
    override fun run() {
        super.run()
    }
}
