package com.wutsi.analytics.tracking.job.step.view

import com.wutsi.analytics.tracking.job.step.AbstractDailyAggregatorJob
import com.wutsi.analytics.tracking.service.stats.view.ViewAggregatorDaily
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ViewDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getName() = "view"
    override fun getAggregator(date: LocalDate) = ViewAggregatorDaily(date)

    @Scheduled(cron = "\${wutsi.application.jobs.daily-view.cron}")
    override fun run() {
        super.run()
    }
}
