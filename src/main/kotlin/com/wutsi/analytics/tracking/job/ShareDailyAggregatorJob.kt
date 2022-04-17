package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.aggregator.product.ShareDailyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShareDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getAggregator(date: LocalDate) = ShareDailyAggregator(date)
    override fun getName() = "share"

    @Scheduled(cron = "\${wutsi.application.jobs.daily-share.cron}")
    override fun run() {
        super.run()
    }
}
