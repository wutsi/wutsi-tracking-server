package com.wutsi.analytics.tracking.job.step.share

import com.wutsi.analytics.tracking.job.step.AbstractDailyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.share.ShareAggregatorDaily
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShareDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getAggregator(date: LocalDate) = ShareAggregatorDaily(date)
    override fun getName() = "share"

    @Scheduled(cron = "\${wutsi.application.jobs.daily-share.cron}")
    override fun run() {
        super.run()
    }
}
