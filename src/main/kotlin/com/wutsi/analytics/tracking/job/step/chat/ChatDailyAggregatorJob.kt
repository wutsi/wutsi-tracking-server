package com.wutsi.analytics.tracking.job.step.chat

import com.wutsi.analytics.tracking.job.step.AbstractDailyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.chat.ChatDailyAggregator
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
